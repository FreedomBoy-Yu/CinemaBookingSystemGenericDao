package eddie.project.cinemabookingsystemgenericdao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomType {
    private Integer roomFloor;
    private char roomSide;
    private Integer roomSize;
    public RoomType(Integer roomFloor, char roomSide, Integer roomSize) {
        this.roomFloor = roomFloor;
        this.roomSide = roomSide;
        this.roomSize = roomSize;
    }
    public RoomType() {

    }

}
