package eddie.project.cinemabookingsystemgenericdao.mapper;

import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BookMapper {
    //Create
    void insertBook(Book book);
    //read
    Book findById(Integer id);
    List<Book> findAll();
    List<Book> findBookByUserId(Integer id);//從userId查詢
    List<Book> findByPaidStatus(Integer status);//尋找訂單狀態，例如 0=未付款, 1=已付款, 2=清除訂單
    List<Book> findBooksByDateRange(Date startDate, Date endDate);//尋找期間內的訂單
    List<Book> findBooksByMovieId(Integer id);
    List<OrderCount> findBookPaidCountByUser(Integer status);//讓資料以 UserOrderCount 模式，根據 status 排序
    List<OrderCount> findMovieOrderCount();//從電影排序訂單數量
    List<OrderCount> findMovieOrderPaidCount(Integer status);//從是否有付款的數量排序
    List<OrderCount> findMovieOrderPaidCountTimeRange(Integer status, Date startDate, Date endDate);
    List<String> BookSeatCheck(Integer movieId);
    //update
    void updateBook(Book book);
    //delete
    void deleteBook(Integer id);
}
