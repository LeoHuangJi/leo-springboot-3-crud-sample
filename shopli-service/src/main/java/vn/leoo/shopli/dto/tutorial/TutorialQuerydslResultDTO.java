package vn.leoo.shopli.dto.tutorial;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.leoo.common.util.DBTable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorialQuerydslResultDTO {
	private String id;
	private String title;

	private String code;

	private String description;


}