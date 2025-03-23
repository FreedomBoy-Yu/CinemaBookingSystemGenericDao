package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.RoomSeatShow;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookCheck;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.dto.book.UserBookListView;
import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.*;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @PutMapping("/update")
    public void update(@RequestHeader("Authorization") String token, @RequestBody UserUpdateDTO userUpdateDTO) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String jwtToken = token.substring(7);
        UserJwtResponseDTO userJwtResponseDTO = userService.jwtTest(jwtToken);
        userUpdateDTO.setId(userJwtResponseDTO.getId());
        userUpdateDTO.setRole(userJwtResponseDTO.getRole());
        userService.updateUser(userUpdateDTO);
    }
    @PutMapping("/changepd")
    public void changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordDTO changePasswordDTO) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String jwtToken = token.substring(7);
        userService.changePassword(jwtToken,changePasswordDTO.getOldPD(), changePasswordDTO.getNewPD());
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userLoginDto) {
        return userService.login(userLoginDto);
    }


    @GetMapping("/userinfo")
    public UserInfo viewUserInfo(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String jwtToken = token.substring(7);
        UserJwtResponseDTO userJwtResponseDTO = userService.jwtTest(jwtToken);
        return userService.viewUserInfo(jwtToken);
    }

    @PostMapping("/jwttest")//測試完成後應該刪除這個功能
    public UserJwtResponseDTO jwtTest(@RequestHeader("Authorization") String token) {
        return userService.jwtTest(token.replace("Bearer ", ""));
    }
    /**********movie查詢相關******************************************************/
    @GetMapping("/movielist")
    public List<MovieDTO> findAllMovie() {
        return movieService.findAll();
    }

    @GetMapping("/movie/findbyname")
    public MovieDTO findByMovieName(@RequestParam("name") String name) {
        return movieService.findByMovieName(name);
    }

    @GetMapping("/movies/name/{movieid}")
    public Map<String, String> findMovieById(@PathVariable Integer movieid) {
        String movieName = movieService.findByMovieId(movieid).getMovieName();
        return Collections.singletonMap("name", movieName); // 讓回應格式變成 { "name": "電影名稱" }
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
    /***************************JPA相關的controller************************************/
    @GetMapping("/userbooklist2")
    public Page<UserBookListView> getUserBooks(@RequestHeader("Authorization") String token,
                                               @RequestParam(defaultValue = "0") int page) {
        Integer userId = userService.jwtToUserId(token.replace("Bearer ", ""));
        return bookService.findBookByUserId(userId, page);
    }
    @GetMapping("/paidBookCheck/{bookId}")
    public UserBookListView getPaidBookCheck(@RequestHeader("Authorization") String token,@PathVariable Integer bookId) {
        Integer userId = userService.jwtToUserId(token.replace("Bearer ", ""));
        return bookService.findBookById(userId,bookId);

    }

    /*********************************************************************************/

    @PutMapping("/cancelupdate") //取消訂單
    public ResponseEntity<String> statusUpdate(@RequestHeader("Authorization") String token,
                                               @RequestBody BookStatusUpdate bookStatusUpdate) {
        Integer userId = userService.jwtToUserId(token.replace("Bearer ", ""));// 解析 JWT 取得 userId
        bookStatusUpdate.setStatus(2);//避免從前端繞過支付功能用取消的方式來欺騙系統付款
        String result = bookService.statusUpdate(userId, bookStatusUpdate);
        return ResponseEntity.ok(result); // 直接回傳成功訊息
    }


    @PutMapping("/seatChange")//使用者更改自己的訂單
    public String bookupdate(@RequestHeader("Authorization") String token, @RequestBody BookStatusUpdate bookStatusUpdate) {
        return bookService.seatChange(bookStatusUpdate);
    }
    @GetMapping("/getseattatusformovie/{movieId}")
    public RoomSeatShow getSeatStatusForMovie(@PathVariable Integer movieId) {
        return bookService.bookSeatShow(movieId);
    }

/*********************************************************************************/


}
