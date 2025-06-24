package vn.leoo.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import vn.leoo.auth.entity.AuthTokenEntity;
import vn.leoo.auth.payload.DeviceSessionDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthTokenMapper {
	List<DeviceSessionDTO> toDTOList(List<AuthTokenEntity> authTokenEntities);

	DeviceSessionDTO toDTO(AuthTokenEntity entity);

//	AuthTokenEntity toEntity(DeviceSessionDTO dto);
}
