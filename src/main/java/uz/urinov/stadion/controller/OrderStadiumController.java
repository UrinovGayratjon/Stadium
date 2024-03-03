package uz.urinov.stadion.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.request.OrderDTO;
import uz.urinov.stadion.entity.OrderEntity;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.service.OrderStadiumService;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderStadiumController {
    private final OrderStadiumService orderStadiumService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderStadiumService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<?> getAllUserOrder() {
        return ResponseEntity.ok(orderStadiumService.findByUserId());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        ApiResponse apiResponse = orderStadiumService.createOrder(orderDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        orderStadiumService.deleteById(id);
        return ResponseEntity.status(200).build();
    }


}
