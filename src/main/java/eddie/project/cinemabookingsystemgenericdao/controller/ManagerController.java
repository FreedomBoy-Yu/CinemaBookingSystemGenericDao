package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.book.OrderCount;
import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private BookService bookService;

    /***********************************************從這裡****************************************/
    @GetMapping("/users/allUserView")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }
    /***********************************************當這裡是管理使用者的controller******************/

    /***********************************************從這裡****************************************/

    @PostMapping("/movies/add")//增加一個新電影
    public void addMovie(@RequestBody MovieDTO movieDTO) {
        movieService.insertMovie(movieDTO);
    }

    @PutMapping("/movies/update")//修改電影資訊
    public void updateMovie(@RequestBody MovieDTO movieDTO) {
        movieService.updateMovie(movieDTO);
    }

    @GetMapping("/movies/allmovies")
    public List<MovieDTO> getAllMovies() {
        return movieService.findAll();
    }
    /***********************************************到這裡是movie相關設置的controller***************/
    /***********************************************從這裡****************************************/


    @GetMapping("/findAllBook")//done
    public List<Book> findAllBook() {
        return bookService.findAll();
    }

    @GetMapping("/status") //被其他功能取代了
    public List<Book> findByPaidStatus(@RequestParam("status") Integer status) {
        return bookService.findByPaidStatus(status);
    }

    @GetMapping("/daterange") // 從訂單時間來篩選所有訂單
    public List<Book> findBooksByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        return bookService.findBooksByDateRange(startDate, endDate);
    }

    @GetMapping("/movieId") // 從電影 id 查詢訂單數量
    public List<Book> findBooksByMovieId(@RequestParam("movieid") Integer movieid) {//done
        return bookService.findBooksByMovieId(movieid);
    }

    @GetMapping("/paidcount") // 查詢並由大到小排序訂單與付款的資料，根據傳入值撈出有付款或尚未付款的資料
    public List<OrderCount> findBookPaidCountByUser(@RequestParam("paid") Integer paid) {
        return bookService.findBookPaidCountByUser(paid);
    }

    @GetMapping("/movieorder") // 從電影排序訂單數量，只顯示數量不顯示其他資訊
    public List<OrderCount> findMovieOrderCount() {//done
        return bookService.findMovieOrderCount();
    }

    @GetMapping("/orderpaidcount") // 用電影的 id 排序已付款的訂單
    public List<OrderCount> findMovieOrderPaidCount(@RequestParam("paid") Integer paid) {
        return bookService.findMovieOrderPaidCount(paid);
    }

    @GetMapping("/paiddaterange") // 用電影的 id 排序已付款的訂單，並設置排序的時間日期
    public List<OrderCount> findMovieOrderPaidCountTimeRange(//done
            @RequestParam("paid") Integer paid,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        if (startDate == null) {
            startDate = LocalDate.of(1999, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return bookService.findMovieOrderPaidCountTimeRange(paid, startDate, endDate);
    }
    @GetMapping("/findbookbyuserid") // 從後台尋找使用者的訂單資訊
    public List<Book> findBookByUserId(@RequestParam("id") Integer id) {
        return bookService.findBookByUserId(id);
    }

    /***********************************************到這裡是Booking相關的設置的controller***********/
}


