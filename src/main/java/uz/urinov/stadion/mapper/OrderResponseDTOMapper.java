package uz.urinov.stadion.mapper;

import org.springframework.stereotype.Component;
import uz.urinov.stadion.dto.response.OrderResponseDTO;
import uz.urinov.stadion.entity.OrderEntity;

@Component
public class OrderResponseDTOMapper implements BaseMapper<OrderResponseDTO, OrderEntity> {
    @Override
    public OrderEntity mapTo(OrderResponseDTO orderResponseDTO) {
        return null;
    }

    @Override
    public OrderResponseDTO mapFrom(OrderEntity orderEntity) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(orderEntity.getId());
        orderResponseDTO.setDate(orderEntity.getDate());
        orderResponseDTO.setStartTime(orderEntity.getStartTime());
        orderResponseDTO.setEndTime(orderEntity.getEndTime());
        orderResponseDTO.setStadiumName(orderEntity.getStadiumEntity().getName());
        return orderResponseDTO;
    }
}
