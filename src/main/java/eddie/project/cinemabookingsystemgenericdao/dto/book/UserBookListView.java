package eddie.project.cinemabookingsystemgenericdao.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserBookListView {
    private Integer id;//訂單id
    private Integer movieId;
    private String seatId;
    private Integer status;
    private Date bookTime;
    private Date payTime;

    public UserBookListView(Integer id, Integer movieId, String seatId, Integer status, Date bookTime, Date payTime) {
        this.id = id;
        this.movieId = movieId;
        this.seatId = seatId;
        this.status = status;
        this.bookTime = bookTime;
        this.payTime = payTime;
    }

    public UserBookListView() {
    }
}
