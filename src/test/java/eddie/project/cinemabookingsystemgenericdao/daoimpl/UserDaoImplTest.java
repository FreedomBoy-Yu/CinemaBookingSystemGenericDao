package eddie.project.cinemabookingsystemgenericdao.daoimpl;


import eddie.project.cinemabookingsystemgenericdao.dao.UserDao;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        // 建立測試用戶
        User user = new User();
        user.setAccount("testUser");
        user.setName("123");
        user.setPhone("0912345678");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(1); // 假設 1 代表一般使用者

        // 執行插入操作
        userDao.insert(user);
    }
    @Test
    public void findtest(){
        System.out.println(userDao.findByUsername("123").getId());//測findByUserName
        System.out.println(userDao.findById(48).getName());//測findById
        System.out.println(userDao.findAll().size());//測試findAll
    }

    @Test
    public void updateTest(){//測試updateUser
        User user = new User();
        user.setId(48);
        user.setAccount("testUser1");
        user.setName("123");
        user.setPhone("0912345678");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(1);
        userDao.update(user);

        userDao.deleteById(48);
    }
}
