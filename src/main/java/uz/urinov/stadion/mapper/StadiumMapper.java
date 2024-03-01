package uz.urinov.stadion.mapper;

import org.springframework.stereotype.Component;
import uz.urinov.stadion.dto.CoordinateDTO;
import uz.urinov.stadion.dto.response.StadiumDTO;
import uz.urinov.stadion.entity.StadiumEntity;

@Component
public class StadiumMapper implements BaseMapper<StadiumEntity, StadiumDTO> {
    @Override
    public StadiumDTO mapTo(StadiumEntity stadiumEntity) {
        StadiumDTO stadiumResponseDTO = new StadiumDTO();
        stadiumResponseDTO.setId(stadiumEntity.getId());
        stadiumResponseDTO.setName(stadiumEntity.getName());
        stadiumResponseDTO.setAddress(stadiumEntity.getAddress());
        stadiumResponseDTO.setContact(stadiumEntity.getContact());
        stadiumResponseDTO.setHourlyPrice(stadiumEntity.getHourlyPrice());
        stadiumResponseDTO.setCoordinateDTO(
                new CoordinateDTO(
                        stadiumEntity.getLat(),
                        stadiumEntity.getLon()
                )
        );
        stadiumResponseDTO.setImageList(stadiumEntity.getAttachmentEntities().stream().map(item -> item.getId()).toList());
        return stadiumResponseDTO;
    }

    @Override
    public StadiumEntity mapFrom(StadiumDTO stadiumResponseDTO) {

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName(stadiumResponseDTO.getName());
        stadiumEntity.setAddress(stadiumResponseDTO.getAddress());
        stadiumEntity.setContact(stadiumResponseDTO.getContact());
        stadiumEntity.setName(stadiumResponseDTO.getName());
        stadiumEntity.setName(stadiumResponseDTO.getName());

        return null;
    }
}
