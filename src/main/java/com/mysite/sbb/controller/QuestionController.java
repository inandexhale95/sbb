package com.mysite.sbb.controller;

import com.mysite.sbb.entity.Pagination;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.form.AnswerForm;
import com.mysite.sbb.form.QuestionForm;
import com.mysite.sbb.mapper.QuestionMapper;
import com.mysite.sbb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionMapper questionMapper;
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionMapper questionMapper, QuestionService questionService) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Pagination pagination = questionService.getPagination(page);

        model.addAttribute("pagination", pagination);
        model.addAttribute("questionList", questionMapper.getPageList(pagination));

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model, AnswerForm answerForm) throws DataNotFoundException {
        Optional<Question> optQuestion = questionMapper.getQuestionById(id);

        if (optQuestion.isPresent()) {
            model.addAttribute("question", optQuestion.get());
            return "question_detail";
        } else {
            throw new DataNotFoundException("해당 게시글은 존재하지 않습니다.");
        }
    }

    @GetMapping("/create")
    public String showQuestionForm(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult, Model model) {
        Question question = makeQuestion(questionForm);

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        // Validation 해야함
        questionMapper.insert(question);
        return "redirect:/question/list";
    }

    private Question makeQuestion(QuestionForm questionForm) {
        Question question = new Question();
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        return question;
    }
}
