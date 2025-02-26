package eddie.project.cinemabookingsystemgenericdao;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserSignInDTO;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import eddie.project.cinemabookingsystemgenericdao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService; // 測試 UserService 的功能

    @Test
    public void testInsertUser() {
        UserSignInDTO userSignInDTO = new UserSignInDTO();
        userSignInDTO.setAccount("money");
        userSignInDTO.setName("Test Name");
        userSignInDTO.setPhone("0912345678");
        userSignInDTO.setEmail("test@example.com");
        userSignInDTO.setPassword("password123");
        userSignInDTO.setRole(1);
        userService.insertUser(userSignInDTO); // 測試插入資料
    }

    @Test
    public void testFindById() {
        User user = userService.findById(52); // 查詢 ID 為 1 的使用者
        System.out.println("找到的使用者名稱：" + user.getName());
    }

    @Test
    public void testFindAll() {
        System.out.print(userService.findAll()); // 確保有資料
    }

//    @Test
//    public void testUpdateUser() {
//        userUpdateDTO userUpdateDTO = userService.findById(51);
//        user.setName("joye");
//        System.out.println(user+"\n");
//        userService.updateUser(userUpdateDTO);
//
//        //assertEquals("Updated Name", updatedUser.getName()); // 確保資料有更新
//    }

    @Test
    public void testDeleteUser() {
        userService.deleteById(54); // 刪除 ID 為 1 的使用者
        assertNull(userService.findById(52)); // 確保已刪除
    }
}
