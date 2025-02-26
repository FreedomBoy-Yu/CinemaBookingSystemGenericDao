package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserLoginDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserUpdateDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.util.JwtUtil;

import java.util.List;


public interface UserService {
    public List<User> findAll();
    public User findById(Integer id);
    public void insertUser(UserSignInDTO userSignInDTO);
    public void updateUser(UserUpdateDTO userUpdateDTO);
    public void deleteById(Integer id);
    public String login(UserLoginDTO userLoginDto);
    public UserJwtResponseDTO jwtTest(String token);

}
