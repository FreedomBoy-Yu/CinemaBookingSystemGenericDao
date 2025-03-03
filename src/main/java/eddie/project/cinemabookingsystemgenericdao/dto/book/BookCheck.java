package eddie.project.cinemabookingsystemgenericdao.dto.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookCheck {
    private Integer movieId;
    private String seatId;

    public BookCheck(Integer movieId, String seatId) {
        this.movieId = movieId;
        this.seatId = seatId;
    }
    public BookCheck() {
    }
}
