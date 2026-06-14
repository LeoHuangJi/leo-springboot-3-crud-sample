package vn.leoo.shopli.service.impl;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.leoo.audit.log.builder.AuditLogContextBuilder;
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

import java.util.Map;

@RequiredArgsConstructor
@Service
public class TutorialImpl implements TutorialService {
	private final TutorialRepository tutorialRepository;
	private final TutorialDAO daoTutorialDAO;
	private final TutorialMapper tutorialMapper;

	private final AuditLogContextBuilder auditBuilder;
	private final ApplicationEventPublisher eventPublisher;
/*	public TutorialImpl(TutorialRepository tutorialRepository, TutorialDAO daoTutorialDAO,
			TutorialMapper tutorialMapper) {
		super();
		this.tutorialRepository = tutorialRepository;
		this.daoTutorialDAO = daoTutorialDAO;
		this.tutorialMapper = tutorialMapper;
	}*/

	@Override
	@Transactional(readOnly = true)
	public ResponseData<TutorialEntity> getById(String id) {

		return tutorialRepository.findById(id)
				.map(entity -> new ResponseData<>(AppErrorCode.SUCCESS, true, Message.OK, entity)).orElseThrow(
						() -> new ServiceException(AppErrorCode.NOT_FOUND, AppErrorCode.NOT_FOUND.getDefaultMessage()));
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public ResponseData<TutorialInputDTO> create(TutorialInputDTO input) {

		TutorialEntity inserted = tutorialRepository.save(tutorialMapper.toEntity(input));

		return ResponseData.created(tutorialMapper.toDto(inserted));
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public ResponseData<TutorialInputDTO> update(String id, TutorialInputDTO updated, HttpServletRequest httpRequest) {
		TutorialEntity ts = tutorialRepository.findById(id).orElseThrow();
		Map<String, String> contextMap = MDC.getCopyOfContextMap();
		String s=	MDC.getMDCAdapter().get("traceId");


		eventPublisher.publishEvent(
				auditBuilder.newLog("HO_SO", "CAP_NHAT_HS")
						.description("Cập nhật hồ sơ: " + id)
						.actorFromSecurityContext(httpRequest)
						.addUpdate("HO_SO", id,ts.deepCopy(),updated)
						.build()
		);


		eventPublisher.publishEvent(
				auditBuilder.newLog("HO_SO", "CAP_NHAT_HS")
						.description("Cập nhật hồ sơ: " + id)
						.actorFromSecurityContext(httpRequest)
						.addUpdate("HO_SO",   id,              hoSoSnap,   hoSo)
						.addUpdate("THU_TUC", thuTuc.getId(),  thuTucSnap, thuTuc)
						.addUpdate("DIA_CHI", diaChi.getId(),  diaChiSnap, diaChi)
						.
						.build()
		);

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