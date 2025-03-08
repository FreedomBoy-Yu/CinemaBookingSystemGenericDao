package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserLoginDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserUpdateDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserSignInDTO userSignInDTO) {
        userSignInDTO.setRole(0);
        userService.insertUser(userSignInDTO);
    }

    @PutMapping("/updateTest")
    public void updateTest(@RequestHeader("Authorization") String token, @RequestBody UserUpdateDTO userUpdateDTO) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String jwtToken = token.substring(7);
        UserJwtResponseDTO userJwtResponseDTO = userService.jwtTest(jwtToken);
        userUpdateDTO.setId(userJwtResponseDTO.getId());

        userUpdateDTO.setRole(0);
        userService.updateUser(userUpdateDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PostMapping("/jwttest")//測試完成後應該刪除這個功能
    public UserJwtResponseDTO jwtTest(@RequestHeader("Authorization") String token) {
        return userService.jwtTest(token.replace("Bearer ", ""));
    }

    @GetMapping("/movielist")
    public List<MovieDTO> findAllMovie() {
        return movieService.findAll();
    }

    @GetMapping("/movie/findbyname")
    public MovieDTO findByMovieName(@RequestParam("name") String name) {
        return movieService.findByMovieName(name);
    }


    /*****************以下是處理book相關功能，要賦予給使用者使用的功能******************/

    @GetMapping("/userBookList")//使用者查看自己的訂單
    public List<Book> findBookByUserId(@RequestHeader("Authorization") String token) {
        Integer id = userService.jwtToUserId(token.replace("Bearer ", ""));
        return bookService.findBookByUserId(id);
    }

    @PostMapping("/userInsertBook")//使用者增加訂單
    public String bookCheck(@RequestHeader("Authorization") String token, @RequestBody BookCheck bookCheck) {
        try {
            Integer id = userService.jwtToUserId(token.replace("Bearer ", ""));
            boolean Checked = bookService.bookCheck(bookCheck);
            if (Checked) {
                bookService.insertBook(id, bookCheck);
                return "預定成功";
            }
        } catch (Exception e) {
            return e.getMessage(); // 捕捉 bookCheck 方法拋出的錯誤訊息
        }
        return "預定失敗"; // 這行理論上不會執行，但保險起見可以加上
    }

    @PutMapping("/paidorchancel")//付款或取消訂單
    public String statusUpdate(@RequestHeader("Authorization") String token, @RequestBody BookStatusUpdate bookStatusUpdate) {
        return bookService.statusUpdate(bookStatusUpdate);
    }

    @PutMapping("/seatChange")//使用者更改自己的訂單
    public String bookupdate(@RequestHeader("Authorization") String token, @RequestBody BookStatusUpdate bookStatusUpdate) {
        return bookService.seatChange(bookStatusUpdate);
    }


/*********************************************************************************/
}
