package eddie.project.cinemabookingsystemgenericdao.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {
    private Integer id;
    private String phone;
    private String email;
    private String password;
    private String name;
    private Integer role;
    public UserUpdateDTO(Integer id, String phone, String account, String email, String password,String name,Integer role) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    public UserUpdateDTO() {

    }

}
