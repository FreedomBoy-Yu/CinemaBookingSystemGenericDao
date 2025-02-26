package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserLoginDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserUpdateDTO;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;



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



}
