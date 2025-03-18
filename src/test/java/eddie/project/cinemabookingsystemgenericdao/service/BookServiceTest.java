package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.exception.DatabaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testFindBookByUserId() {
        List<Book> books = bookService.findBookByUserId(1);
        assertNotNull(books);
        assertFalse(books.isEmpty());
        System.out.println("查詢結果數量: " + books.size());
        books.forEach(book -> System.out.println(book.getUserId() + " " + book.getBookTime()));
    }

    @Test
    public void testInsertBook() {
        BookCheck bookCheck = new BookCheck();
        bookCheck.setMovieId(1);
        bookCheck.setSeatId("5A");

        bookService.insertBook(1, bookCheck);
        System.out.println("成功新增訂單");
    }

    @Test
    @Transactional
    void testInsertBookRollback() {
        BookCheck bookCheck = new BookCheck();
        bookCheck.setMovieId(9); // ❌ 這裡假設 9999 是不存在的電影 ID，會導致異常
        bookCheck.setSeatId("5A");

        assertThrows(DatabaseException.class, () -> bookService.insertBook(1, bookCheck));

        // 確認資料庫內沒有這筆訂單（應該 rollback）
        List<Book> books = bookService.findBookByUserId(1);
        assertTrue(books.stream().noneMatch(book -> "5A".equals(book.getSeatId())),
                "錯誤！座位 5A 仍然存在，表示 rollback 失敗！");
    }


    @Test
    public void testUpdateBook() {
        Book book = bookService.findBookByUserId(1).get(0);
        book.setStatus(1);
        bookService.updateBook(book);

        Book updatedBook = bookService.findBookByUserId(1).get(0);
        assertEquals(1, updatedBook.getStatus());
        System.out.println("成功更新訂單狀態");
    }

    @Test
    public void testStatusUpdate() {
        BookStatusUpdate statusUpdate = new BookStatusUpdate();
        statusUpdate.setId(1);
        statusUpdate.setStatus(1);

        String result = bookService.statusUpdate(1,statusUpdate);
        System.out.println(result);
        assertTrue(result.contains("訂單已付款") || result.contains("訂單已取消"));
    }

    @Test
    public void testSeatChange() {
        BookStatusUpdate seatUpdate = new BookStatusUpdate();
        seatUpdate.setId(1);
        seatUpdate.setSeatid("6B");

        String result = bookService.seatChange(seatUpdate);
        System.out.println(result);
        assertEquals("座位已更換", result);
    }
}
