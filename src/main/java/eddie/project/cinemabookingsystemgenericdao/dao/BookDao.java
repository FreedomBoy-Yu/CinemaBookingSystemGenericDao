package eddie.project.cinemabookingsystemgenericdao.dao;

import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;

import java.util.Date;
import java.util.List;

public interface BookDao extends GenericDao<Book, Integer>{
    List<Book> findBookByUserId(Integer id);//從userId查詢
    List<Book> findByPaidStatus(boolean isPaid);//尋找是否有付費，也就是最終有沒有來看電影
    List<Book> findBooksByDateRange(Date startDate, Date endDate);//尋找期間內的訂單
    List<Book> findBooksByMovieId(Integer id);


    List<OrderCount> findBookPaidCountByUser(Boolean paid);//查詢並由大到小排序訂單與付款的資料根據傳入值撈出有付款或尚未付款的資料

    List<OrderCount> findMovieOrderCount();//從電影排序訂單數量只顯示數量不顯示其他資訊

    List<OrderCount> findMovieOrderPaidCount(Boolean paid);
}
