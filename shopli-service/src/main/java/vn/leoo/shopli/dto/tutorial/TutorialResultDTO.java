package vn.leoo.shopli.dto.tutorial;

import java.math.BigDecimal;
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
public class TutorialResultDTO {
	@DBTable(columnName = "id")
	private String id;
	@DBTable(columnName = "title")
	private String title;

	@DBTable(columnName = "code")
	private String code;

	@DBTable(columnName = "description")
	private String description;

	@DBTable(columnName = "published")
	private Integer published;

	@DBTable(columnName = "parent_id")
	private Long parentId;
	@DBTable(columnName = "CREATEDDATE")
	private Timestamp createdDate;
	
	@DBTable(columnName = "image")
	private byte[] image;

}
