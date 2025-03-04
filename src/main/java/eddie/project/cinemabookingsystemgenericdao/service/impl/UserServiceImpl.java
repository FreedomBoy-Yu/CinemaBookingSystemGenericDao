package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserLoginDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserUpdateDTO;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.dao.UserDao;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.exception.CustomNotFoundException;
import eddie.project.cinemabookingsystemgenericdao.exception.DatabaseException;
import eddie.project.cinemabookingsystemgenericdao.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        return Optional.ofNullable(userDao.findById(id))
                .orElseThrow(() -> new CustomNotFoundException("找不到使用者 ID: " + id));
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

        try {
            userDao.insert(user);
        } catch (DataAccessException e) {
            throw new DatabaseException("資料庫錯誤，無法新增使用者", e);
        }
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        User user = Optional.ofNullable(userDao.findById(userUpdateDTO.getId()))
                .orElseThrow(() -> new CustomNotFoundException("找不到使用者 ID: " + userUpdateDTO.getId()));

        // ✅ 使用 Optional 避免 null
        Optional.ofNullable(userUpdateDTO.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userUpdateDTO.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateDTO.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(userUpdateDTO.getName()).ifPresent(user::setName);
        Optional.ofNullable(userUpdateDTO.getRole()).ifPresent(user::setRole);

        try {
            userDao.update(user);
        } catch (DataAccessException e) {
            throw new DatabaseException("資料庫錯誤，無法更新使用者", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            userDao.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseException("資料庫錯誤，無法刪除使用者 ID: " + id, e);
        }
    }

    // ✅ 登入方法，生成 JWT Token
    public String login(UserLoginDTO userLoginDto) {
        User user = Optional.ofNullable(userDao.Login(userLoginDto.getAccount(), userLoginDto.getPassword()))
                .orElseThrow(() -> new CustomNotFoundException("帳號或密碼錯誤"));

        UserJwtResponseDTO userJwtResponseDTO = new UserJwtResponseDTO(user.getId(), user.getAccount(), user.getRole());

        try {
            return jwtUtil.generateToken(userJwtResponseDTO);
        } catch (Exception e) {
            throw new RuntimeException("JWT 生成失敗", e);
        }
    }

    @Override
    public UserJwtResponseDTO jwtTest(String token) {
        try {
            return jwtUtil.validateToken(token);
        } catch (Exception e) {
            throw new RuntimeException("JWT 驗證失敗", e);
        }
    }

    @Override
    public Integer jwtToUserId(String token) {
        try {
            return jwtUtil.validateToken(token).getId();
        } catch (Exception e) {
            throw new RuntimeException("JWT 解析失敗", e);
        }
    }
}
