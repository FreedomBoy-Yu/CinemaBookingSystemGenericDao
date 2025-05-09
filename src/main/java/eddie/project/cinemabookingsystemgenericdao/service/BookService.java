package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.RoomSeatShow;
import eddie.project.cinemabookingsystemgenericdao.dto.RoomType;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.dto.book.UserBookListView;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
/*以下的iport是jpa關的page*/
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/*到這裡*/

public interface BookService {
    //create
    void insertBook(Integer Userid, BookCheck bookCheck);
    //Read
    //已生成jaserreport報表
    List<Book> findAll();

    List<Book> findBookByUserId(Integer id);//從userId查詢
    //已生成jaserreport報表
    List<Book> findByPaidStatus(Integer status);//尋找是否有付費，也就是最終有沒有來看電影
    //已生成jaserreport報表
    List<Book> findBooksByDateRange(Date startDate, Date endDate);//尋找期間內的訂單
    //已生成jaserreport報表
    List<Book> findBooksByMovieId(Integer id);
    //已生成jaserreport報表
    List<OrderCount> findBookPaidCountByUser(Integer status);//查詢並由大到小排序訂單與付款的資料根據傳入值撈出有付款或尚未付款的資料
    //已生成jaserreport報表
    List<OrderCount> findMovieOrderCount();//從電影排序訂單數量只顯示數量不顯示其他資訊
    //已生成jaserreport報表
    List<OrderCount> findMovieOrderPaidCount(Integer paid);//用電影的id排序已付款的訂單

    List<OrderCount> findMovieOrderPaidCountTimeRange(Integer paid, LocalDate startDate, LocalDate endDate);//用電影的id排序已付款的訂單，並設置排序的時間日期

    List<String> bookSeatCheck(BookCheck bookCheck);

    RoomSeatShow bookSeatShow(Integer movieId);

    RoomType roomTypeCheck(BookCheck bookCheck);

    boolean bookCheck(BookCheck bookCheck) throws Exception;
    //update
    void updateBook(Book book);

    String statusUpdate(Integer uid,BookStatusUpdate bookStatusUpdate);

    String seatChange(BookStatusUpdate bookStatusUpdate);

    //delete
    void deleteBook(Book book);

    //
    Page<UserBookListView> findBookByUserId(Integer userId, int page);

    UserBookListView findBookById(Integer UID,Integer bookId);

}
