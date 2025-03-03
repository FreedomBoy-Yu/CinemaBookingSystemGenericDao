package eddie.project.cinemabookingsystemgenericdao.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class BookStatusUpdate {//訂單狀態更新
    private Integer id;
    private Integer status;
    private String seatid;

    public BookStatusUpdate(Integer id, Integer status, Date doingDate) {
        this.id = id;
        this.status = status;
    }
    public BookStatusUpdate() {
    }
}
