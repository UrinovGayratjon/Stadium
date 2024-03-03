package uz.urinov.stadion.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.response.StadiumDTO;
import uz.urinov.stadion.service.StadiumService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/stadiums")
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping()
    public ResponseEntity<?> getAllStadium(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon
    ) {
        List<StadiumDTO> allStadiums = stadiumService.getAllStadiums(
                startTime,
                endTime,
                lat,
                lon
        );
        return ResponseEntity.ok(allStadiums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStadiumById(@PathVariable Long id) {
        return ResponseEntity.ok(stadiumService.getStadiumById(id));
    }

    @PostMapping()
    public ResponseEntity<?> saveStadium(@RequestBody StadiumDTO requestDTO) {
        stadiumService.saveStadium(requestDTO);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStadium(@PathVariable Long id, @RequestBody StadiumDTO requestDTO) {
        ApiResponse apiResponse = stadiumService.updateStadium(id, requestDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        ApiResponse apiResponse = stadiumService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}
