package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/book/findAllBook")
    public List<Book> findAllBook() {
        return bookService.findAll();
    }


    /***********************************************到這裡是Booking相關的設置的controller***********/
    }


