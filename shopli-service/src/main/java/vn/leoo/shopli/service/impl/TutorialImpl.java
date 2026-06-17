package vn.leoo.shopli.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.leoo.audit.log.util.DeepCopyUtil;
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
import vn.leoo.audit.log.builder.AuditLogContextBuilder;
import vn.leoo.audit.log.publish.AuditEventPublisher;

@RequiredArgsConstructor
@Service
public class TutorialImpl implements TutorialService {
    private final TutorialRepository tutorialRepository;
    private final TutorialDAO daoTutorialDAO;
    private final TutorialMapper tutorialMapper;

    private final AuditLogContextBuilder auditLogContextBuilder;
    private final AuditEventPublisher eventPublisher;
    private final DeepCopyUtil deepCopyUtil;


    @Override
    @Transactional(readOnly = true)
    public ResponseData<TutorialEntity> getById(String id) {

        return tutorialRepository.findById(id)
                .map(entity -> new ResponseData<>(AppErrorCode.SUCCESS, true, Message.OK, entity)).orElseThrow(
                        () -> new ServiceException(AppErrorCode.NOT_FOUND, AppErrorCode.NOT_FOUND.getDefaultMessage()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResponseData<TutorialInputDTO> create(TutorialInputDTO input) {

        TutorialEntity inserted = tutorialRepository.save(tutorialMapper.toEntity(input));

        return ResponseData.created(tutorialMapper.toDto(inserted));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResponseData<TutorialInputDTO> update(String id, TutorialInputDTO updated) {
        TutorialEntity ts = tutorialRepository.findById(id).orElseThrow();
        TutorialEntity tutorialSnap = deepCopyUtil.copy(ts);
        TutorialEntity tutorialEntity = tutorialMapper.toEntity(updated);
        tutorialRepository.save(tutorialEntity);
        for (int i=0;i<10;i++){
            tutorialSnap.setTitle(updated.getTitle()+i);
            AuditLogContextBuilder.Builder auditBuilder = auditLogContextBuilder
                    .newLog("HO_SO", "HO_SO_DUYET", "HO_SO", id);

            auditBuilder.addUpdate("HO_SO", id, tutorialSnap, tutorialEntity); // ← append

            auditBuilder.addUpdate("TAP", id, tutorialSnap, tutorialEntity);  // ← append

            eventPublisher.publish(auditBuilder.build());
        }


        return ResponseData.ok(updated);

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