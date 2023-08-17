package com.ctrlcv.ersentinel_springboot.service;


import com.ctrlcv.ersentinel_springboot.data.entity.Survey;
import com.ctrlcv.ersentinel_springboot.data.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SurveyService {
    //TODO: 컨트롤러에서 validation하는 코드 작성해야 함 !!!! 무적권!!!!
    @Autowired
    private SurveyRepository surveyRepository;

    public Optional<Survey[]> retrieveSurveyByDutyId(final String dutyId) {
        return surveyRepository.findByHospitalDutyId(dutyId);
    }

//    public Optional<Survey[]> retrieve(final String userId) {
//        return surveyRepository.findByUserId(userId);
//    }

    public Optional<Survey[]> create(final Survey entity) {

        surveyRepository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());
        return surveyRepository.findByHospitalDutyId(entity.getHospital().getDutyId());
    }

//    private void validate(final Survey entity) {
//        if(entity == null) {
//            log.warn("Entity cannot be null.");
//            throw new RuntimeException("Entity cannot be null.");
//        }

//        if(entity.getUserId() == null) {
//            log.warn("Unknown user.");
//            throw new RuntimeException("Unknown user.");
//        }
//    }




    public List<Survey> update(final Survey entity) {
        final Optional<Survey> original = surveyRepository.findById(entity.getId());

        original.ifPresent(survey -> {
            survey.setStar(entity.getStar());
            todo.setDone(entity.isDone());

            // (4) 데이터베이스에 새 값을 저장한다.
            repository.save(todo);
        });

        // 2.3.2 Retrieve Todo에서 만든 메서드를 이용해 유저의 모든 Todo 리스트를 리턴한다.
        return retrieve(entity.getUserId());
    }


    public List<TodoEntity> delete(final TodoEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다. 이 메서드는 2.3.1 Create Todo에서 구현했다.
        validate(entity);

        try {
            // (2) 엔티티를 삭제한다.
            repository.delete(entity);
        } catch(Exception e) {
            // (3) exception 발생시 id와 exception을 로깅한다.
            log.error("error deleting entity ", entity.getId(), e);

            // (4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        // (5) 새 Todo리스트를 가져와 리턴한다.
        return retrieve(entity.getUserId());
    }




}
