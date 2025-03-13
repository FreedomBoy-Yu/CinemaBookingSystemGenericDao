package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.dto.RoomSeatShow;
import eddie.project.cinemabookingsystemgenericdao.dto.RoomType;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.exception.CustomNotFoundException;
import eddie.project.cinemabookingsystemgenericdao.exception.DatabaseException;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private MovieDao movieDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBook(Integer userId, BookCheck bookCheck) {
        Book book = new Book();
        book.setUserId(userId);
        book.setMovieId(bookCheck.getMovieId());
        book.setSeatId(bookCheck.getSeatId());
        book.setStatus(0);

        try {
            bookDao.insert(book);
        } catch (PersistenceException e) {
            throw new DatabaseException("資料庫錯誤，新增訂單失敗", e);
        }
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findBookByUserId(Integer id) {
        return bookDao.findBookByUserId(id);
    }

    @Override
    public List<Book> findByPaidStatus(Integer status) {
        return bookDao.findByPaidStatus(status);
    }

    @Override
    public List<Book> findBooksByDateRange(Date startDate, Date endDate) {
        return bookDao.findBooksByDateRange(startDate, endDate);
    }

    @Override
    public List<Book> findBooksByMovieId(Integer id) {
        return bookDao.findBooksByMovieId(id);
    }

    @Override
    public List<OrderCount> findBookPaidCountByUser(Integer paid) {
        return bookDao.findBookPaidCountByUser(paid);
    }

    @Override
    public List<OrderCount> findMovieOrderCount() {
        return bookDao.findMovieOrderCount();
    }

    @Override
    public List<OrderCount> findMovieOrderPaidCount(Integer status) {
        return bookDao.findMovieOrderPaidCount(status);
    }

    @Override
    public List<OrderCount> findMovieOrderPaidCountTimeRange(Integer status, Date startDate, Date endDate) {
        return bookDao.findMovieOrderPaidCountTimeRange(status, startDate, endDate);
    }

    @Override
    public List<String> bookSeatCheck(BookCheck bookCheck) {//查看目前的電影定位狀況
        return bookDao.bookSeatCheck(bookCheck.getMovieId());
    }




/*
* 一層樓是15個橫向座位，屬於大影廳
* 二層樓是10個橫向座位，屬於中影聽
* 三層樓是7個橫向座位,屬於小影廳
* */

    @Override
    public RoomType roomTypeCheck(BookCheck bookCheck) {
        String roomWay = Optional.ofNullable(movieDao.findById(bookCheck.getMovieId()))
                .orElseThrow(() -> new CustomNotFoundException("找不到電影 ID: " + bookCheck.getMovieId()))
                .getRoomWay();

        RoomType roomType = new RoomType();
        roomType.setRoomFloor(Character.getNumericValue(roomWay.charAt(0)));//取得影廳的樓層
        roomType.setRoomSide(roomWay.charAt(1));//取得影廳的樓層位置

        switch (roomType.getRoomFloor()) {
            case 1 -> roomType.setRoomSize(15);
            case 2 -> roomType.setRoomSize(10);
            case 3 -> roomType.setRoomSize(7);
            default -> throw new IllegalArgumentException("無效的影廳樓層: " + roomType.getRoomFloor());
        }
        return roomType;
    }

    @Override
    public RoomSeatShow bookSeatShow(Integer movieId) {//查看目前的電影定位狀況
        RoomSeatShow roomSeatShow = new RoomSeatShow();
        char roomway=movieDao.findById(movieId).getRoomWay().charAt(0);//將影廳的編號一曲出來

        switch (roomway) {
            case '1' -> roomSeatShow.setRoomSize(15);
            case '2' -> roomSeatShow.setRoomSize(10);
            case '3' -> roomSeatShow.setRoomSize(7);
            default -> throw new IllegalArgumentException("無效的影廳樓層: " + roomway);
        }
        List<String> bookedSeats = bookDao.bookSeatCheck(movieId);
        if (bookedSeats != null) {
            roomSeatShow.setSeat(bookedSeats.toArray(new String[0])); // 轉換 List<String> 為 String[]
        } else {
            roomSeatShow.setSeat(new String[0]); // 避免 NullPointerException
        }
        return roomSeatShow;
    }

    @Override
    public boolean bookCheck(BookCheck bookCheck) {
        String seatId = bookCheck.getSeatId();

        if (!seatId.matches("^[1-9][A-O]$")) {
            throw new IllegalArgumentException("座位格式錯誤");
        }

        if (bookSeatCheck(bookCheck).contains(seatId)) {
            throw new IllegalStateException("該座位已經被預訂");
        }

        if (roomTypeCheck(bookCheck).getRoomSize() < (seatId.charAt(1) - 'A' + 1)) {
            throw new IllegalArgumentException("沒有該座位");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBook(Book book) {
        try {
            bookDao.update(book);
        } catch (PersistenceException e) {
            throw new DatabaseException("資料庫錯誤，更新訂單失敗", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String statusUpdate(BookStatusUpdate bookStatusUpdate) {
        try {
            Book book = Optional.ofNullable(bookDao.findById(bookStatusUpdate.getId()))
                    .orElseThrow(() -> new CustomNotFoundException("找不到訂單 ID: " + bookStatusUpdate.getId()));

            book.setStatus(bookStatusUpdate.getStatus());
            book.setPayTime(new Date());

            bookDao.update(book);

            return switch (bookStatusUpdate.getStatus()) {
                case 1 -> "訂單已付款，付款時間：" + book.getPayTime();
                case 2 -> "訂單已取消，取消時間：" + book.getPayTime();
                default -> "未知狀態更新";
            };

        } catch (CustomNotFoundException e) {
            return "更新失敗：" + e.getMessage();
        } catch (PersistenceException e) {
            throw new DatabaseException("資料庫錯誤，更新失敗", e);
        } catch (Exception e) {
            throw new RuntimeException("發生未知錯誤，更新失敗", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String seatChange(BookStatusUpdate bookStatusUpdate) {
        try {
            Book book = Optional.ofNullable(bookDao.findById(bookStatusUpdate.getId()))
                    .orElseThrow(() -> new CustomNotFoundException("找不到訂單 ID: " + bookStatusUpdate.getId()));

            BookCheck bookCheck = new BookCheck();
            bookCheck.setMovieId(book.getMovieId());
            bookCheck.setSeatId(bookStatusUpdate.getSeatid());

            if (bookCheck(bookCheck)) {
                book.setSeatId(bookStatusUpdate.getSeatid());
                updateBook(book);
                return "座位已更換";
            }
            return "座位更換失敗，座位不可用";
        } catch (CustomNotFoundException e) {
            return "座位更換失敗：" + e.getMessage();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return "座位更換失敗：" + e.getMessage();
        } catch (PersistenceException e) {
            throw new DatabaseException("資料庫錯誤，座位更換失敗", e);
        } catch (Exception e) {
            throw new RuntimeException("發生未知錯誤，座位更換失敗", e);
        }
    }

    @Override
    public void deleteBook(Book book) {
        try {
            bookDao.deleteById(book.getId());
        } catch (PersistenceException e) {
            throw new DatabaseException("資料庫錯誤，刪除訂單失敗", e);
        }
    }
}
