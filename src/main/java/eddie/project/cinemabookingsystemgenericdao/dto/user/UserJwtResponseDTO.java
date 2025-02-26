package eddie.project.cinemabookingsystemgenericdao.dto.user;

public class UserJwtResponseDTO {
    private Integer id;
    private String account;
    private Integer role;
    public UserJwtResponseDTO(Integer id, String account, int role) {
        this.id = id;
        this.account = account;
        this.role = role;
    }
    public UserJwtResponseDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
