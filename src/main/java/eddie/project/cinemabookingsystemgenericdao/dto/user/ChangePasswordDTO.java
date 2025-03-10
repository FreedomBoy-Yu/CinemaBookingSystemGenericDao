package eddie.project.cinemabookingsystemgenericdao.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordDTO {
    private String oldPD;
    private String newPD;

    public ChangePasswordDTO(String oldPD, String newPD) {
        this.oldPD = oldPD;
        this.newPD = newPD;
    }

    public ChangePasswordDTO() {
    }
}
