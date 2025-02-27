package eddie.project.cinemabookingsystemgenericdao.dto.book;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class OrderCount {//這是一個判斷使用者是否付費的排序資料傳輸層
    private Integer userId;
    private Integer movieId;
    private Date bookTime;
    private Date payTime;
    public OrderCount(Integer userId, Integer movieId, Date bookTime, Date payTime) {
        this.userId = userId;
        this.movieId = movieId;
        this.bookTime = bookTime;
        this.payTime = payTime;
    }
    public OrderCount() {

    }

}
