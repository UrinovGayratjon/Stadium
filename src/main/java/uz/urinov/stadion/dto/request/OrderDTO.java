package uz.urinov.stadion.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OrderDTO {
    private Long stadiumId;
    private String date;
    private String startTime;
    private String endTime;
}
