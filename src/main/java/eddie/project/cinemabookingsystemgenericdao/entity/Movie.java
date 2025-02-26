package eddie.project.cinemabookingsystemgenericdao.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private Integer id;
    private String roomWay;
    private String movieName;
    private Boolean bookAble;
    public Movie(Integer id, String roomWay, String movieName, Boolean bookAble) {
        this.id = id;
        this.roomWay = roomWay;
        this.movieName = movieName;
        this.bookAble = bookAble;
    }
    public Movie(){

    }

}
