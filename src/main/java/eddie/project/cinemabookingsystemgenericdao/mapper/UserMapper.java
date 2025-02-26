package eddie.project.cinemabookingsystemgenericdao.mapper;

import eddie.project.cinemabookingsystemgenericdao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    User findById(int id);
    void insertUser(User user);
    void updateUser(User user);
    void deleteById(int id);
    User findByUsername(String username);
    User LoginByAccount(String account,String password);
    List<User> findAll();
}
