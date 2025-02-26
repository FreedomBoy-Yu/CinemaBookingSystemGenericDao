package eddie.project.cinemabookingsystemgenericdao.dto.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInDTO {
    private String phone;
    private String account;
    private String email;
    private String password;
    private String name;
    private Integer role;

    public UserSignInDTO(String phone, String account, String email, String password, String name, Integer role) {
        this.phone = phone;
        this.account = account;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    public UserSignInDTO() {}

}
