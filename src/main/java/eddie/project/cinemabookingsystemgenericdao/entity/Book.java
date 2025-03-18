package eddie.project.cinemabookingsystemgenericdao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity  // JPA 註解，表示這是一個實體
@Table(name = "books")  // 指定對應的資料表名稱
public class Book {

    @Id  // 設定主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自動增長
    private Integer id;

    @Column(name = "user_id")  // 指定資料庫欄位名稱
    private Integer userId;

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "seat_id")
    private String seatId;

    private Integer status;

    @Column(name = "book_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookTime;

    @Column(name = "pay_time")
    @Temporal(TemporalType.TIMESTAMP)
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
