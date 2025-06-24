package vn.leoo.shopli.controller;

import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.leoo.common.constants.Constants;
import vn.leoo.common.dto.PageResponse;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.entity.TutorialEntity;
import vn.leoo.shopli.dto.tutorial.TutorialFilterDTO;
import vn.leoo.shopli.dto.tutorial.TutorialInputDTO;
import vn.leoo.shopli.dto.tutorial.TutorialQuerydslResultDTO;
import vn.leoo.shopli.dto.tutorial.TutorialResultDTO;
import vn.leoo.shopli.service.TutorialService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/tutorial")
public class TutorialController {
	   @Autowired
	    private MessageSource mgsSource;
	@Autowired
	private TutorialService tutorialService;

	@GetMapping("/test-message")
    public String testMessage() {
        return mgsSource.getMessage("test.message", null, Locale.ENGLISH);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData<TutorialEntity>> getById(@PathVariable String id) {
		ResponseData<TutorialEntity> response = tutorialService.getById(id);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@PutMapping("/{id}")@Operation(summary="Cập nhật tutorial theo ID")@ApiResponses(value={@ApiResponse(responseCode="200",description="Cập nhật thành công",content=@Content(mediaType="application/json",schema=@Schema(implementation=ResponseData.class))),

	@ApiResponse(responseCode="400",description="Không tìm thấy",content=@Content(mediaType="application/json")),

	@ApiResponse(responseCode="500",description="Lỗi hệ thống",content=@Content(mediaType="application/json"))

	})

	public ResponseEntity<ResponseData<TutorialInputDTO>> update(@PathVariable String id,
			@Valid  @RequestBody TutorialInputDTO dto) {
		ResponseData<TutorialInputDTO> response = tutorialService.update(id, dto);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping("/tutorials")
	@Operation(summary = "Tìm kiếm tutorial", description = "Trả về danh sách, số lượng bản ghi, phân trang")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Thành công, trả về tutorial"),
			@ApiResponse(responseCode="500",description="Lỗi hệ thống",content=@Content(mediaType="application/json")) })

	public ResponseEntity<ResponseData<PageResponse<TutorialResultDTO>>> getAllTutorials(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "published", required = false) Integer  published,

			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size

	) {

		page = ObjectUtils.isEmpty(page) ? Constants.VALID.MIN_PAGE : page;
		size = ObjectUtils.isEmpty(size) ? Constants.VALID.MIN_SIZE : size;
		TutorialFilterDTO filter = TutorialFilterDTO.builder().keyword(keyword).title(title).description(description)
				.published(published).page(page).size(size).build();

		ResponseData<PageResponse<TutorialResultDTO>> res = tutorialService.search(filter);
		return ResponseEntity.status(res.getHttpStatus()).body(res);
	}
	
	@GetMapping("/tutorials2")
	@Operation(summary = "Tìm kiếm tutorial", description = "Trả về danh sách, số lượng bản ghi, phân trang")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Thành công, trả về tutorial"),
			@ApiResponse(responseCode="500",description="Lỗi hệ thống",content=@Content(mediaType="application/json")) })

	public ResponseEntity<ResponseData<PageResponse<TutorialQuerydslResultDTO>>> getAllTutorials2(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "published", required = false) Integer  published,

			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size

	) {

		page = ObjectUtils.isEmpty(page) ? Constants.VALID.MIN_PAGE : page;
		size = ObjectUtils.isEmpty(size) ? Constants.VALID.MIN_SIZE : size;
		TutorialFilterDTO filter = TutorialFilterDTO.builder().keyword(keyword).title(title).description(description)
				.published(published).page(page).size(size).build();

		ResponseData<PageResponse<TutorialQuerydslResultDTO>> res = tutorialService.searchQuerydsl(filter);
		return ResponseEntity.status(res.getHttpStatus()).body(res);
	}

}
