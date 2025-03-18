package eddie.project.cinemabookingsystemgenericdao.dao.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dao.BookRepository;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.dto.book.UserBookListView;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookMapper bookmapper;
    //Create
    /********************************************************/
    @Override
    public void insert(Book book) {//test_ok
        book.setBookTime(new Date());
        bookmapper.insertBook(book);
    }
    //read
    @Override
    public Book findById(Integer id) {//test_ok
        System.out.println(bookmapper.findById(id).getUserId());
        return bookmapper.findById(id);
    }

    @Override
    public List<Book> findAll() {//test_ok
        return bookmapper.findAll();
    }
    @Override
    public List<Book> findBookByUserId(Integer id){//test_ok
        return bookmapper.findBookByUserId(id);
    }
    @Override
    public List<Book> findByPaidStatus(Integer status) {//test_ok
        return bookmapper.findByPaidStatus(status);
    }
    @Override
    public List<Book> findBooksByDateRange(Date startDate, Date endDate) {//test_ok
        return bookmapper.findBooksByDateRange(startDate, endDate);
    }
    @Override
    public List<Book> findBooksByMovieId(Integer id) {//test_ok
        return bookmapper.findBooksByMovieId(id);
    }
    @Override
    public List<OrderCount> findBookPaidCountByUser(Integer status) {//test_ok
        return bookmapper.findBookPaidCountByUser(status);
    }
    @Override
    public List<OrderCount> findMovieOrderCount(){//test_ok
        return bookmapper.findMovieOrderCount();
    }
    @Override
    public List<OrderCount> findMovieOrderPaidCount(Integer status){//test_ok
        return bookmapper.findMovieOrderPaidCount(status);
    }

    @Override
    public List<OrderCount> findMovieOrderPaidCountTimeRange(Integer status, Date startDate, Date endDate){
        return bookmapper.findMovieOrderPaidCountTimeRange(status, startDate, endDate);
    }

    /********************************************************/
    @Override
    public List<String> bookSeatCheck(Integer movieId){
        return bookmapper.bookSeatCheck(movieId);
    }
    //update
    @Override
    public void update(Book book) {
        bookmapper.updateBook(book);

    }
    //delete
    @Override
    public void deleteById(Integer id) {
        bookmapper.deleteBook(id);
        //訂單紀錄不會刪除,只是不會顯示於使用者端
    }


}
