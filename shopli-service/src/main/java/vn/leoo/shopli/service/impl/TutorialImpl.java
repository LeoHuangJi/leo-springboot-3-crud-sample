package vn.leoo.shopli.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.constants.Message;
import vn.leoo.common.dto.PageResponse;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.common.exception.ServiceException;
import vn.leoo.entity.TutorialEntity;
import vn.leoo.shopli.dao.TutorialDAO;
import vn.leoo.shopli.dto.tutorial.TutorialFilterDTO;
import vn.leoo.shopli.dto.tutorial.TutorialInputDTO;
import vn.leoo.shopli.dto.tutorial.TutorialQuerydslResultDTO;
import vn.leoo.shopli.dto.tutorial.TutorialResultDTO;
import vn.leoo.shopli.mapper.TutorialMapper;
import vn.leoo.shopli.repository.TutorialRepository;
import vn.leoo.shopli.service.TutorialService;

@Service
public class TutorialImpl implements TutorialService {
	private TutorialRepository tutorialRepository;
	private TutorialDAO daoTutorialDAO;
	private final TutorialMapper tutorialMapper;

	public TutorialImpl(TutorialRepository tutorialRepository, TutorialDAO daoTutorialDAO,
			TutorialMapper tutorialMapper) {
		super();
		this.tutorialRepository = tutorialRepository;
		this.daoTutorialDAO = daoTutorialDAO;
		this.tutorialMapper = tutorialMapper;
	}

	@Override
	public ResponseData<TutorialEntity> getById(String id) {

		return tutorialRepository.findById(id)
				.map(entity -> new ResponseData<>(AppErrorCode.SUCCESS, true, Message.OK, entity)).orElseThrow(
						() -> new ServiceException(AppErrorCode.NOT_FOUND, AppErrorCode.NOT_FOUND.getDefaultMessage()));
	}

	@Override
	public ResponseData<TutorialInputDTO> create(TutorialInputDTO input) {

		TutorialEntity inserted = tutorialRepository.save(tutorialMapper.toEntity(input));

		return ResponseData.created(tutorialMapper.toDto(inserted));
	}

	@Override
	public ResponseData<TutorialInputDTO> update(String id, TutorialInputDTO updated) {
		if ("F56564941B434C68BF8415BE66449FB5".equals(id)) {
			return ResponseData.badRequest("bad request", updated);
		}

		return tutorialRepository.findById(id).map(existing -> {
			tutorialMapper.toEntity(updated);
			tutorialRepository.save(existing);
			return ResponseData.ok(updated);
		}).orElse(ResponseData.notFound(Message.NOT_FOUND));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseData<PageResponse<TutorialResultDTO>> search(TutorialFilterDTO filter) {
		try {
			PageResponse<TutorialResultDTO> lst = daoTutorialDAO.search(filter);
			return ResponseData.ok(lst);
		} catch (Exception e) {
			throw new ServiceException(AppErrorCode.INTERNAL_ERROR, Message.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseData<PageResponse<TutorialQuerydslResultDTO>> searchQuerydsl(TutorialFilterDTO filter) {
		try {
			PageResponse<TutorialQuerydslResultDTO> lst = daoTutorialDAO.searchQuerydsl(filter);
			return ResponseData.ok(lst);
		} catch (Exception e) {
			throw new ServiceException(AppErrorCode.INTERNAL_ERROR, Message.INTERNAL_SERVER_ERROR);
		}
	}

}