package eddie.project.cinemabookingsystemgenericdao.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfo {
    private String account;
    private String name;
    private String email;
    private String phone;
    private Integer role;

    public UserInfo(String account, String name, String email, String phone, Integer role) {
        this.account = account;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
    public UserInfo() {

    }
}
