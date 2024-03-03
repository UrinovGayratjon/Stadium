package uz.urinov.stadion.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urinov.stadion.config.CurrentUserProvider;
import uz.urinov.stadion.config.Util;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.request.OrderDTO;
import uz.urinov.stadion.entity.OrderEntity;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.repository.OrderRepository;
import uz.urinov.stadion.repository.StadiumRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderStadiumService {
    private final OrderRepository orderRepository;
    private final StadiumRepository stadiumRepository;

    public ApiResponse createOrder(OrderDTO orderDTO) {
        StadiumEntity byId = stadiumRepository.findById(orderDTO.getStadiumId()).orElseThrow();
        LocalDate date = LocalDate.parse(orderDTO.getDate());
        LocalTime startTime = LocalTime.parse(orderDTO.getStartTime());
        LocalTime endTime = LocalTime.parse(orderDTO.getEndTime());
        if (!startTime.isBefore(endTime)) {
            return new ApiResponse("Vaqt Xato", false);
        }

        List<OrderEntity> byDate = orderRepository.findByStadiumEntityAndDate(byId, date, startTime, endTime);

        if (!byDate.isEmpty()) {
            return (new ApiResponse("Vaqt band etilgan!!!", false));
        }
        UserEntity principal = CurrentUserProvider.getCurrentUser();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(date);
        orderEntity.setStartTime(startTime);
        orderEntity.setEndTime(endTime);
        orderEntity.setStadiumEntity(byId);
        orderEntity.setOrderOwner(principal);

        orderRepository.save(orderEntity);

        return new ApiResponse("Success", true);
    }


    public OrderEntity findById(Long id) {
        UserEntity userEntity = CurrentUserProvider.getCurrentUser();
        return orderRepository.findByIdAndOrderOwnerId(id, userEntity.getId()).orElseThrow();
    }

    public void deleteById(Long id) {
        UserEntity userEntity = CurrentUserProvider.getCurrentUser();
        orderRepository.deleteByIdAndOrderOwnerId(id, userEntity.getId());
    }

    public List<OrderEntity> findByUserId() {
        UserEntity userEntity = CurrentUserProvider.getCurrentUser();
        return orderRepository.findByOrderOwnerId(userEntity.getId());
    }
}
