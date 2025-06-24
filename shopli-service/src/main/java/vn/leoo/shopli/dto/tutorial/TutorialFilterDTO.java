package vn.leoo.shopli.dto.tutorial;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TutorialFilterDTO {

	private String keyword;
	private String title;
	private String description;
	private Integer published;
	
	private Integer page;
	private Integer size;
	
	private String sortColumn;
	private String sortDirection;
}
