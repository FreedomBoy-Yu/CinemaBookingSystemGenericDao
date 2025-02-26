package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserLoginDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserUpdateDTO;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.dao.DaoException;
import eddie.project.cinemabookingsystemgenericdao.dao.UserDao;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserDao userDao, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Integer id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new DaoException("User not found with id: " + id);
        }
        return user;
    }

    @Override
    public void insertUser(UserSignInDTO userSignInDTO) {
        User user = new User();
        user.setPhone(userSignInDTO.getPhone());
        user.setAccount(userSignInDTO.getAccount());
        user.setEmail(userSignInDTO.getEmail());
        user.setPassword(userSignInDTO.getPassword());
        user.setName(userSignInDTO.getName());
        user.setRole(userSignInDTO.getRole());
        userDao.insert(user);
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userDao.findById(userUpdateDTO.getId());

        // 使用 Optional.ofNullable() 來簡化 null 檢查
        Optional.ofNullable(userUpdateDTO.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userUpdateDTO.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateDTO.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(userUpdateDTO.getName()).ifPresent(user::setName);
        Optional.ofNullable(userUpdateDTO.getRole()).ifPresent(user::setRole);

        userDao.update(user);
    }


    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    // ✅ 登入方法，生成 JWT Token
    public String login(UserLoginDTO userLoginDto) {
        User user = userDao.Login(userLoginDto.getAccount(), userLoginDto.getPassword());

        // ✅ 檢查 user 是否為 null，避免 NullPointerException
        if (user == null) {
            throw new DaoException("Invalid account or password");
        }

        // ✅ 簡化 UserJwtResponseDTO 的初始化
        UserJwtResponseDTO userJwtResponseDTO = new UserJwtResponseDTO(user.getId(), user.getAccount(), user.getRole());

        // ✅ 產生 JWT Token
        return jwtUtil.generateToken(userJwtResponseDTO);
    }

    @Override
    public UserJwtResponseDTO jwtTest(String token) {
        return jwtUtil.validateToken(token);
    }
}
