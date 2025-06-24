package vn.leoo.shopli.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import vn.leoo.entity.TutorialEntity;
import vn.leoo.shopli.dto.tutorial.TutorialInputDTO;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface TutorialMapper {
	 // Entity → DTO
		/*
		 * @Mapping(source = "createdDate", target = "createdDate", dateFormat =
		 * "yyyy-MM-dd HH:mm:ss")
		 * 
		 * @Mapping(source = "parent_id", target = "parentId")
		 * 
		 * @Mapping(source = "category_id", target = "categoryId") TutorialDto
		 * toDto(TutorialEntity entity);
		 * 
		 * List<TutorialDto> toDtoList(List<TutorialEntity> entities);
		 * 
		 * // DTO → Entity (nếu cần)
		 * 
		 * @Mapping(source = "parentId", target = "parent_id")
		 * 
		 * @Mapping(source = "categoryId", target = "category_id")
		 * 
		 * @InheritInverseConfiguration(name = "toDto")
		 */
    TutorialEntity toEntity(TutorialInputDTO dto);
    TutorialInputDTO toDto(TutorialEntity entity);

}
