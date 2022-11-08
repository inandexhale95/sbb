package com.mysite.sbb.service;

import com.mysite.sbb.entity.Pagination;
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

    public Pagination getPagination(int page) {
        Pagination pagination = new Pagination();
        pagination.pageSet(page, questionMapper.getMaxQuestionId());

        return pagination;
    }
}
