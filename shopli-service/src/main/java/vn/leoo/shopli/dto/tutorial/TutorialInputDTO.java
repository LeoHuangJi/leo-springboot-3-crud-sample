package vn.leoo.shopli.dto.tutorial;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorialInputDTO {
	
	private String id;

    @NotBlank(message = "{error.field.validation.NotBlank}")
    @Size(max = 255, message =  "{error.field.validation.maxLength}")
    private String title;

    @NotBlank(message = "{error.field.validation.NotBlank}")
    @Size(max = 100, message = "{error.field.validation.maxLength}")
    private String code;

    @Size(min=20, max = 1000, message = "{error.field.validation.minMaxLength}")
    private String description;

    private boolean published;

    private String parentId;

    @NotNull(message = "{error.field.validation.NotBlank}")
    private Long categoryId;

}