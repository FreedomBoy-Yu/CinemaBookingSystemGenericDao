package eddie.project.cinemabookingsystemgenericdao.entity;

public class User {
    private Integer id;
    private String phone;
    private String account;
    private String email;
    private String password;
    private String name;
    private Integer role;

    public User(Integer id, String phone, String account, String email, String password,String name,Integer role) {
        this.id = id;
        this.phone = phone;
        this.account = account;
        this.email = email;
        this.password = password;
        this.name=name;
        this.role=role;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
