package eddie.project.cinemabookingsystemgenericdao.dto.user;

public class UserLoginDTO {
    private String account;
    private String password;
    public UserLoginDTO(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
