package vn.leoo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrentUserDTO {
	private String id;
	private String account;
	private Long type;
	private String organization;

}
