package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.user.*;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.util.JwtUtil;

import java.util.List;


public interface UserService {
    public List<User> findAll();
    public User findById(Integer id);
    public UserInfo viewUserInfo(String token);
    public void insertUser(UserSignInDTO userSignInDTO);
    public void updateUser(UserUpdateDTO userUpdateDTO);
    public void deleteById(Integer id);
    public String login(UserLoginDTO userLoginDto);
    public UserJwtResponseDTO jwtTest(String token);
    public Integer jwtToUserId(String token);
    public String changePassword(String token, String oldPD, String newPD);
}
