package eddie.project.cinemabookingsystemgenericdao.service;


import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Test
    public void findBookByUserIdTest() {
        Book book = bookService.findBookByUserId(1).get(0);
        System.out.println(book.getUserId()+" "+book.getBookTime());
    }

}
