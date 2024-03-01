package uz.urinov.stadion.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.response.StadiumDTO;
import uz.urinov.stadion.entity.AttachmentEntity;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.mapper.StadiumMapper;
import uz.urinov.stadion.repository.AttachmentRepository;
import uz.urinov.stadion.repository.StadiumRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StadiumService {
    private final StadiumMapper stadiumMapper;
    private final StadiumRepository stadiumRepository;
    private final AttachmentRepository attachmentRepository;

    public List<StadiumDTO> getAllStadiums(
            String startTime,
            String endTime,
            Double lat,
            Double lon
    ) {
        try {
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startTime);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endTime);

            LocalDate date = startLocalDateTime.toLocalDate();
            LocalTime startLocalTime = startLocalDateTime.toLocalTime();
            LocalTime endLocalTime = endLocalDateTime.toLocalTime();

            List<StadiumEntity> entityList = stadiumRepository.getAllBySortingByFreeTime(lat, lon);
            List<StadiumEntity> list = entityList.stream().filter(entity -> !entity.getOrderEntityList().stream().anyMatch(orderEntity -> {
                LocalTime startTime1 = orderEntity.getStartTime();
                LocalTime endTime1 = orderEntity.getEndTime();
                int i = startLocalTime.compareTo(startTime1);
                int j = startLocalTime.compareTo(endTime1);
                int k = endLocalTime.compareTo(startTime1);
                int y = endLocalTime.compareTo(endTime1);
                boolean b = startLocalTime.isBefore(startTime1) && endLocalTime.isAfter(endTime1);
                return (i >= 0 && j < 0) || (k > 0 && y <= 0) || b;
            })).toList();
            List<StadiumDTO> responseDTOList = list.stream().map(stadiumMapper::mapTo).toList();
            return responseDTOList;
        } catch (Exception e) {
        }
        List<StadiumEntity> entityList = stadiumRepository.getAllBySorting(lat, lon);
        List<StadiumDTO> responseDTOList = entityList.stream().map(stadiumMapper::mapTo).toList();
        return responseDTOList;
    }

    public StadiumDTO getStadiumById(Long id) {
        Optional<StadiumEntity> stadiumEntity = stadiumRepository.findById(id);
        return stadiumEntity.map(stadiumMapper::mapTo).orElseThrow();
    }

    public void saveStadium(StadiumDTO requestDTO) {
        StadiumEntity stadiumEntity = new StadiumEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity principal = (UserEntity) authentication.getPrincipal();

        stadiumEntity.setName(requestDTO.getName());
        stadiumEntity.setAddress(requestDTO.getAddress());
        stadiumEntity.setContact(requestDTO.getContact());
        stadiumEntity.setLat(requestDTO.getCoordinateDTO().getLat());
        stadiumEntity.setLon(requestDTO.getCoordinateDTO().getLon());
        stadiumEntity.setHourlyPrice(requestDTO.getHourlyPrice());
        stadiumEntity.setOwner(principal);

        List<AttachmentEntity> attachmentEntities = attachmentRepository.findAllById(requestDTO.getImageList());

        stadiumEntity.setAttachmentEntities(
                attachmentEntities
        );

        stadiumRepository.save(stadiumEntity);
    }


    public ApiResponse updateStadium(Long id, StadiumDTO requestDTO) {
        Optional<StadiumEntity> stadiumEntity = stadiumRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity principal = (UserEntity) authentication.getPrincipal();

        if (stadiumEntity.isEmpty() || !principal.getUsername().equals(stadiumEntity.get().getOwner().getUsername())) {
            return new ApiResponse("Bunday Maydon mavjud emas!", false);
        }
        StadiumEntity stadiumEntity1 = stadiumEntity.get();
        stadiumEntity1.setName(requestDTO.getName());
        stadiumEntity1.setAddress(requestDTO.getAddress());
        stadiumEntity1.setContact(requestDTO.getContact());
        stadiumEntity1.setLon(requestDTO.getCoordinateDTO().getLon());
        stadiumEntity1.setLat(requestDTO.getCoordinateDTO().getLat());
        stadiumEntity1.setHourlyPrice(requestDTO.getHourlyPrice());
        stadiumRepository.save(stadiumEntity1);
        return new ApiResponse("Success", true);
    }
}
