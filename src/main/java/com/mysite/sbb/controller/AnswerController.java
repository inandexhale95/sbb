package com.mysite.sbb.controller;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.form.AnswerForm;
import com.mysite.sbb.mapper.AnswerMapper;
import com.mysite.sbb.mapper.QuestionMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    private QuestionMapper questionMapper;
    private AnswerMapper answerMapper;

    @Autowired
    public AnswerController(QuestionMapper questionMapper, AnswerMapper answerMapper) {
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
    }

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable int id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) throws NotFoundException {
        Optional<Question> optQuestion = questionMapper.getQuestionById(id);

        if (optQuestion.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("question", optQuestion.get());
                return "question_detail";
            }

            Answer answer = makeAnswer(answerForm, id);

            answerMapper.insert(answer);
            return "redirect:/question/detail/" + id;
        } else {
            throw new NotFoundException("해당 게시글은 존재하지 않습니다.");
        }
    }

    private Answer makeAnswer(AnswerForm answerForm, int questionId) {
        Answer answer = new Answer();
        answer.setContent(answerForm.getContent());
        answer.setQuestionId(questionId);
        return answer;
    }
}
