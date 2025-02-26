package eddie.project.cinemabookingsystemgenericdao.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieDTO {
    private Integer id;
    private String movieName;
    private Boolean bookAble;
    private String roomWay;
    public MovieDTO(String movieName, String roomWay, Boolean bookAble) {
        this.movieName = movieName;
        this.bookAble = bookAble;
        this.roomWay = roomWay;
    }
    public MovieDTO(Integer id,String movieName, String roomWay, Boolean bookAble) {
        this.id=id;
        this.movieName = movieName;
        this.bookAble = bookAble;
        this.roomWay = roomWay;
    }
    public MovieDTO() {
    }

}
