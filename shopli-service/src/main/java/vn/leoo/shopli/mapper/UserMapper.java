package vn.leoo.shopli.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import vn.leoo.shopli.dto.user.User;
import vn.leoo.shopli.dto.user.UserDTO;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}