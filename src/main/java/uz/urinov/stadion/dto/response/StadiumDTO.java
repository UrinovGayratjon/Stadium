package uz.urinov.stadion.dto.response;

import lombok.Data;
import uz.urinov.stadion.dto.CoordinateDTO;

import java.util.List;

@Data
public class StadiumDTO {
    private Long id;
    private String name;
    private String contact;
    private String address;
    private Long hourlyPrice;
    private CoordinateDTO coordinateDTO;
    private List<Long> imageList;
}
