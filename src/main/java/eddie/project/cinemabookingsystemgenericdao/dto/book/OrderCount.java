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
    private Integer orderCount;
    public OrderCount(Integer userId, Integer movieId, Date bookTime, Date payTime, Integer orderCount) {
        this.userId = userId;
        this.movieId = movieId;
        this.bookTime = bookTime;
        this.payTime = payTime;
        this.orderCount = orderCount;
    }
    public OrderCount() {

    }

}
