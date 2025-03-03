package eddie.project.cinemabookingsystemgenericdao.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book{
    private Integer id;
    private Integer userId;
    private Integer movieId;
    private String seatId;
    private Integer status;
    private Date bookTime;
    private Date payTime;

    public Book() {}

    public Book(Integer id, Integer userId, Integer movieId, String seatId, Integer status, Date bookTime, Date payTime) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.seatId = seatId;
        this.status = status;
        this.bookTime = bookTime;
        this.payTime = payTime;
    }
}
