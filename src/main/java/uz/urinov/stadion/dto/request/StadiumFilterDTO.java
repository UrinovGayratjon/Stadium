package uz.urinov.stadion.dto.request;

import lombok.Data;
import uz.urinov.stadion.dto.CoordinateDTO;

@Data
public class StadiumFilterDTO {
    private String date;
    private String startTime;
    private String endTime;
    private CoordinateDTO coordinateDTO;
}
