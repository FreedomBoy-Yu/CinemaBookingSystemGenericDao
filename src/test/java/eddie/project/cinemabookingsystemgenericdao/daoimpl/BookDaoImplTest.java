package eddie.project.cinemabookingsystemgenericdao.daoimpl;


import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BookDaoImplTest {
    @Autowired
    private BookDao bookDao;
    @Test
    public void insert() {
        Book book = new Book();
        book.setStatus(0);
        book.setMovieId(9);
        book.setUserId(7);
        book.setSeatId("1C");
        bookDao.insert(book);
    }
    @Test
    public void findById(){
        System.out.println(bookDao.findById(3).getUserId());
    }
    @Test
    public void update() {
        Book book =bookDao.findById(7);
        book.setPayTime(new Date());
        book.setStatus(1);
        bookDao.update(book);
    }
    @Test
    public void findByMovieId() {
        bookDao.findAll().forEach(System.out::println);
    }
    @Test
    public void testFindBookByUserId() {
        List<Book> books = bookDao.findBookByUserId(4);

        if (books == null || books.isEmpty()) {
            System.out.println("查無 userId=4 的訂單！");
        } else {
            for (Book book : books) {
                System.out.println("訂單ID: " + book.getId() +
                        ", 電影ID: " + book.getMovieId() +
                        ", 訂單狀態: " + book.getStatus());
            }
        }
    }
    @Test
    public void findByStatusTest() {
        bookDao.findByPaidStatus(1).forEach(System.out::println);
        System.out.println(bookDao.findByPaidStatus(0).size());
        int i=0;
        i=i+bookDao.findByPaidStatus(1).size();
        i=i+bookDao.findByPaidStatus(0).size();
        System.out.println(i);
    }
    @Test
    public void findBookByDateRangeTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse("2025-02-10 19:08:23");

        Date endDate = formatter.parse("2025-02-18 19:08:33");
        System.out.println(bookDao.findBooksByDateRange(startDate,endDate).size());
    }
    @Test
    public void findBooksByMovieIdTest(){
        System.out.println(bookDao.findBooksByMovieId(5).size());
    }
    @Test
    public void findBooksByUserTest() {
        List<OrderCount> userOrderCountList = bookDao.findBookPaidCountByUser(0);
        for (OrderCount userOrderCount : userOrderCountList) {
            System.out.println("User ID: " + userOrderCount.getUserId()+"\t"+"電影的ID:"+userOrderCount.getMovieId()+"\t"+"付款時間:"+userOrderCount.getPayTime());
        }
        System.out.println(userOrderCountList.size());
    }
    @Test
    public void findMovieOrderCountTest(){
        List<OrderCount> OrderCountList = bookDao.findMovieOrderCount();
        for (OrderCount OrderCount : OrderCountList) {
            System.out.println("movie_id: " + OrderCount.getMovieId()+"\t"+"訂單數量:"+OrderCount.getOrderCount());
        }
        System.out.println(OrderCountList.size());
    }
    @Test
    public void findMovieOrderPaidCountTest(){
        List<OrderCount> OrderCountList = bookDao.findMovieOrderPaidCount(2);

        for (OrderCount OrderCount : OrderCountList) {
            System.out.println("movie_id: " + OrderCount.getMovieId()+"\t"+"訂單數量:"+OrderCount.getOrderCount());
        }
        System.out.println(OrderCountList.size());
    }
    @Test
    public void findMovieOrderPaidCountTimeRangeTest()throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse("2025-02-10 19:08:23");

        Date endDate = formatter.parse("2025-03-02 19:08:23");
        List<OrderCount> OrderCountList = bookDao.findMovieOrderPaidCountTimeRange(2,startDate,endDate);
        for (OrderCount OrderCount : OrderCountList) {
            System.out.println("movie_id: " + OrderCount.getMovieId()+"\t"+"訂單數量:"+OrderCount.getOrderCount());
        }
        System.out.println(OrderCountList.size());
    }
    @Test
    public void BookSeatCheckTest(){
        bookDao.BookSeatCheck(3).forEach(System.out::println);
    }
}
