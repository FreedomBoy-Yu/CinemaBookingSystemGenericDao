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
    List<Book> findByPaidStatus(boolean isPaid);//尋找是否有付費，也就是最終有沒有來看電影
    List<Book> findBooksByDateRange(Date startDate, Date endDate);//尋找期間內的訂單
    List<Book> findBooksByMovieId(Integer id);
    List<OrderCount> findBookPaidCountByUser(@Param("paid") Integer paid);//讓資料以UserOrderCount的模式，排序userid當中paid=true
    List<OrderCount> findMovieOrderCount();//從電影排序訂單數量
    List<OrderCount> findMovieOrderPaidCount(Boolean paid);//從是否有付款的數量排序
    //update
    void updateBook(Book book);
    //delete
    void deleteBook(Book book);


}
