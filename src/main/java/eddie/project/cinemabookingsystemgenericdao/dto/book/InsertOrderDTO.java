package eddie.project.cinemabookingsystemgenericdao.dto.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertOrderDTO {
    private Integer movieid;
    private String seatid;

    public InsertOrderDTO(Integer movieid, String seatid) {
        this.movieid = movieid;
        this.seatid = seatid;
    }

    public InsertOrderDTO() {
    }
}
