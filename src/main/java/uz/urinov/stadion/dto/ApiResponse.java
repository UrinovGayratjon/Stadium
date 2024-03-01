package uz.urinov.stadion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
}
