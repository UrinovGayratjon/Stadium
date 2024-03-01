package uz.urinov.stadion.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotNull
    private String fullName;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String prePassword;

    private boolean isStadiumOwner = false;
}
