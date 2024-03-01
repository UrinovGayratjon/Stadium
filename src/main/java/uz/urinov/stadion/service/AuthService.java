package uz.urinov.stadion.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.request.RegisterRequestDTO;
import uz.urinov.stadion.entity.enums.RoleEnum;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.repository.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterRequestDTO requestDto) {

        if (!requestDto.getPassword().equals(requestDto.getPrePassword())) {
            return new ApiResponse("Parol bir hil emas!!!", false);
        }

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new ApiResponse("User name mavjud ", false);
        }

        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .username(requestDto.getUsername())
                .fullName(requestDto.getFullName())
                .role(requestDto.isStadiumOwner() ? RoleEnum.STADIUM_OWNER : RoleEnum.USER)
                .build();

        userRepository.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yhatdan o'tti", true);
    }

    public ApiResponse registerAdminUser(RegisterRequestDTO requestDto) {

        if (!requestDto.getPassword().equals(requestDto.getPrePassword())) {
            return new ApiResponse("Parol bir hil emas!!!", false);
        }

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new ApiResponse("User name mavjud ", false);
        }

        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .username(requestDto.getUsername())
                .fullName(requestDto.getFullName())
                .role(RoleEnum.ADMIN)
                .build();

        userRepository.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yhatdan o'tti", true);

    }
}
