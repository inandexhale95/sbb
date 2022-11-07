package com.mysite.sbb.service;

import com.mysite.sbb.entity.Question;
import com.mysite.sbb.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {

    private QuestionMapper questionMapper;

    @Autowired
    public QuestionService(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    public List<Question> getList(int page) {
        final int pageItems = 10;
        int maxId = questionMapper.getMaxQuestionId();  // Question PK 마지막 값
        int pageId = maxId - (page * pageItems);        // 페이징 시작할 id PK

        int totalPageCount = Math.floorDiv(maxId, pageItems);   // 총 페이지 수

        if (totalPageCount > page) {
            // 총 페이지 수를 초과하면 예외
        }

        return questionMapper.getPageList(pageId);
    }
}
