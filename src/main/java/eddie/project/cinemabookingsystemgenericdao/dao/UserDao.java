package eddie.project.cinemabookingsystemgenericdao.dao;

import eddie.project.cinemabookingsystemgenericdao.entity.User;

public interface UserDao extends GenericDao<User,Integer> {
    //額外方法
    User findByUsername(String username);
    User Login (String account, String password);
}
