package eddie.project.cinemabookingsystemgenericdao.dao;

import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookDao extends GenericDao<Book, Integer>{
    List<Book> findBookByUserId(Integer id);//從userId查詢
    List<Book> findByPaidStatus(Integer status);//尋找訂單狀態，例如未付款、已付款、已取消
    List<Book> findBooksByDateRange(Date startDate, Date endDate);//尋找期間內的訂單
    List<Book> findBooksByMovieId(Integer id);
    List<OrderCount> findBookPaidCountByUser(Integer status);//查詢並由大到小排序訂單與付款的資料根據傳入值撈出對應狀態的資料
    List<OrderCount> findMovieOrderCount();//從電影排序訂單數量只顯示數量不顯示其他資訊
    List<OrderCount> findMovieOrderPaidCount(Integer status);//用電影的id排序已付款的訂單
    List<OrderCount> findMovieOrderPaidCountTimeRange(Integer status, LocalDate startDate, LocalDate endDate);//用電影的id排序已付款的訂單，並設置排序的時間日期
    List<String> bookSeatCheck(Integer movieId);
}
