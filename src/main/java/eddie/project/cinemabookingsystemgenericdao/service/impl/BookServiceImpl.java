package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.dto.RoomType;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.dto.book.InsertOrderDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMapManager;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private MovieDao movieDao;

    @Override
    public void insertBook(Integer UserId, BookCheck bookCheck) {//增加新訂單的功能
        Book book = new Book();
        book.setUserId(UserId);
        book.setMovieId(bookCheck.getMovieId());
        book.setSeatId(bookCheck.getSeatId());
        book.setStatus(0);
        bookDao.insert(book);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findBookByUserId(Integer id) {//從使用者ID尋找該使用者的訂單
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
    public List<String> bookSeatCheck(BookCheck bookCheck){//確認訂位電影廳已被定位的的座位
        return bookDao.BookSeatCheck(bookCheck.getMovieId());
    }
    /******
           電影院有三層
           影廳:
           第一層有4個影廳，第二層有7個影廳，第三層有10個影廳
           每個影廳編號會由樓層跟英文字母來排列
           第一層的開頭為1,第二層的開頭為2,第三層的開頭為3
           層數之間，每個影廳的編號用英文字母來排列

           座位:
           第一層是大影廳，第二層是中影聽，第三層是小影廳
           每一層的排數是相等的，行數是不相等的，行數會以英文字母顯示
           第一層有15行，第二層有10行,第三層有7行
     */
    @Override
    public RoomType roomTypeCheck(BookCheck bookCheck){

        String roomWay=movieDao.findById(bookCheck.getMovieId()).getRoomWay();
        RoomType roomType= new RoomType();
        roomType.setRoomFloor(Character.getNumericValue(roomWay.charAt(0)));
        roomType.setRoomSide(roomWay.charAt(1));

        if(roomType.getRoomFloor()==1){
            roomType.setRoomSize(15);
        } else if (roomType.getRoomFloor()==2) {
            roomType.setRoomSize(10);
        }else if (roomType.getRoomFloor()==3){
            roomType.setRoomSize(7);
        }
        return roomType;
    }//通過給予電影名稱來確定電影的廳型

    @Override
    public boolean bookCheck(BookCheck bookCheck) throws Exception {
        String seatId = bookCheck.getSeatId();
        // 1️⃣ 檢查格式是否正確
        if (!seatId.matches("^[1-9][A-O]$")) {
            throw new Exception("Oh~看來你喜歡用作弊的方式來訂位，但違反我的規則了");
        }
        // 2️⃣ 檢查座位是否已被預訂
        if (bookSeatCheck(bookCheck).contains(seatId)) {
            throw new Exception("該座位已經被預訂");
        }
        // 3️⃣ 檢查座位是否超出房間範圍
        if (roomTypeCheck(bookCheck).getRoomSize() < (seatId.charAt(1) - 'A' + 1)) {
            throw new Exception("沒有該座位");
        }
        // 4️⃣ 以上條件都通過，回傳 true
        return true;
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public String statusUpdate(BookStatusUpdate bookStatusUpdate){
        Book book=bookDao.findById(bookStatusUpdate.getId());//取得原訂單資訊
        book.setStatus(bookStatusUpdate.getStatus());
        book.setPayTime(new Date());

        if(bookStatusUpdate.getStatus()==1){
            bookDao.update(book);
            return "訂單已付款\t付款時間為\t"+book.getPayTime();
        }else if(bookStatusUpdate.getStatus()==2){
            bookDao.update(book);
            return "訂單取消\t取消時間為\t"+book.getPayTime();
        }
        return null;
    }

    @Override
    public String seatChange(BookStatusUpdate bookStatusUpdate) {
        try {
            Book book = bookDao.findById(bookStatusUpdate.getId());
            // 創建一個新的 BookCheck 來儲存使用者預更新的資料
            BookCheck bookCheck = new BookCheck();
            bookCheck.setMovieId(book.getMovieId());
            bookCheck.setSeatId(bookStatusUpdate.getSeatid());

            if (bookCheck(bookCheck)) {
                book.setSeatId(bookStatusUpdate.getSeatid());
                updateBook(book);
                return "座位已更換";
            }
        } catch (Exception e) {
            return "座位更換失敗：" + e.getMessage(); // 直接回傳錯誤訊息
        }
        return "座位更換失敗";
    }



    @Override
    public void deleteBook(Book book) {
        bookDao.deleteById(book.getId());
    }

}
