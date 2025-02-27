package eddie.project.cinemabookingsystemgenericdao.dao.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookMapper bookmapper;
    //Create
    /********************************************************/
    @Override
    public void insert(Book book) {//test_ok
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
    public List<Book> findByPaidStatus(boolean isPaid) {//test_ok
        return bookmapper.findByPaidStatus(isPaid);
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
    public List<OrderCount> findBookPaidCountByUser(Boolean paid) {//test_ok
        if(paid){
            return bookmapper.findBookPaidCountByUser(1);
        }else{
            return bookmapper.findBookPaidCountByUser(0);
        }

    }
    @Override
    public List<OrderCount> findMovieOrderCount(){//
        return bookmapper.findMovieOrderCount();
    }
    @Override
    public List<OrderCount> findMovieOrderPaidCount(Boolean paid){
        return bookmapper.findMovieOrderPaidCount(paid);
    }


    /********************************************************/

    //update
    @Override
    public void update(Book book) {

        bookmapper.updateBook(book);
    }
    //delete
    @Override
    public void deleteById(Integer integer) {
        //訂單紀錄不會刪除
    }
}
