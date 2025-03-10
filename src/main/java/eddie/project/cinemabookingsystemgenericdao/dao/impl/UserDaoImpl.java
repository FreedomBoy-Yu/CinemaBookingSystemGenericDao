package eddie.project.cinemabookingsystemgenericdao.dao.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.UserDao;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import eddie.project.cinemabookingsystemgenericdao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User Login(String account, String password) {
        return userMapper.LoginByAccount(account, password);
    }


    @Override
    public void insert(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User findById(Integer integer) {
        return userMapper.findById(integer);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void update(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteById(Integer integer) {
        userMapper.deleteById(integer);
    }

}
