package eddie.project.cinemabookingsystemgenericdao.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RoomSeatShow {
    private Integer roomSize;
    private String[] seat;

    public RoomSeatShow(Integer roomSize, String[] seat) {
        this.roomSize = roomSize;
        this.seat = seat;
    }

    public RoomSeatShow() {
        this.roomSize = 0;
        this.seat = new String[0];
    }
}
