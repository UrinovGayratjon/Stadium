package uz.urinov.stadion.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.stadion.config.JwtProvider;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.request.LoginRequestDTO;
import uz.urinov.stadion.dto.request.RegisterRequestDTO;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.service.AuthService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        ApiResponse apiResponse = authService.registerUser(requestDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@RequestBody RegisterRequestDTO requestDTO) {
        ApiResponse apiResponse = authService.registerAdminUser(requestDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );
        UserEntity user = (UserEntity) authenticate.getPrincipal();
        String generateToken = jwtProvider.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(generateToken);
    }


}
