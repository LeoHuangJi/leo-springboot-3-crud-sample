package vn.leoo.shopli.service;

import vn.leoo.common.dto.PageResponse;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.entity.TutorialEntity;
import vn.leoo.shopli.dto.tutorial.TutorialFilterDTO;
import vn.leoo.shopli.dto.tutorial.TutorialInputDTO;
import vn.leoo.shopli.dto.tutorial.TutorialQuerydslResultDTO;
import vn.leoo.shopli.dto.tutorial.TutorialResultDTO;

public interface TutorialService {
	ResponseData<PageResponse<TutorialResultDTO>> search(TutorialFilterDTO filter);

	ResponseData<TutorialEntity> getById(String id);

	ResponseData<TutorialInputDTO> update(String id, TutorialInputDTO updated);

	ResponseData<TutorialInputDTO> create(TutorialInputDTO input);
	 ResponseData<PageResponse<TutorialQuerydslResultDTO>> searchQuerydsl(TutorialFilterDTO filter);
}