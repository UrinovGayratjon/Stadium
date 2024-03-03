package uz.urinov.stadion.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import uz.urinov.stadion.config.CurrentUserProvider;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.dto.response.StadiumDTO;
import uz.urinov.stadion.entity.AttachmentEntity;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.exceptions.ResourceNotFoundException;
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
        return stadiumEntity.map(stadiumMapper::mapTo).orElseThrow(() -> new ResourceNotFoundException("Stadium not Found!!!"));
    }

    public StadiumEntity saveStadium(StadiumDTO requestDTO) {
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
        try {
            List<AttachmentEntity> attachmentEntities = attachmentRepository.findAllById(requestDTO.getImageList());

            stadiumEntity.setAttachmentEntities(
                    attachmentEntities
            );
        } catch (Exception e) {

        }


     return stadiumRepository.save(stadiumEntity);
    }


    public ApiResponse updateStadium(Long id, StadiumDTO requestDTO) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(id).orElseThrow();

        UserEntity principal = CurrentUserProvider.getCurrentUser();

        if (!principal.getId().equals(stadiumEntity.getOwner().getId())) {
            return new ApiResponse("Bunday Maydon mavjud emas!", false);
        }

        stadiumEntity.setName(requestDTO.getName());
        stadiumEntity.setAddress(requestDTO.getAddress());
        stadiumEntity.setContact(requestDTO.getContact());
        stadiumEntity.setLon(requestDTO.getCoordinateDTO().getLon());
        stadiumEntity.setLat(requestDTO.getCoordinateDTO().getLat());
        stadiumEntity.setHourlyPrice(requestDTO.getHourlyPrice());
        stadiumRepository.save(stadiumEntity);
        return new ApiResponse("Success", true);
    }

    public ApiResponse deleteById(Long id) {
        UserEntity currentUser = CurrentUserProvider.getCurrentUser();
        boolean byIdAndOwnerId = stadiumRepository.deleteByIdAndOwnerId(id, currentUser.getId());
        if (byIdAndOwnerId) {
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Error", false);
    }
}
