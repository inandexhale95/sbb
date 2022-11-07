package com.mysite.sbb;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.mapper.AnswerMapper;
import com.mysite.sbb.mapper.QuestionMapper;
import com.mysite.sbb.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private AnswerMapper answerMapper;

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		this.questionMapper.insert(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		this.questionMapper.insert(q2);
	}

	@Test
	void getAll() {
		List<Question> questionList = this.questionMapper.getAll();
		assertEquals(2, questionList.size());

		for (Question question : questionList) {
			System.out.println(question.getId() + " " + question.getSubject());
		}
	}

	@Test
	void getById() {
		Optional<Question> oq = this.questionMapper.getQuestionById(1);

		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void getBySubject() {
		Question oq = this.questionMapper.getQuestionBySubject("sbb가 무엇인가요?");
		assertEquals(1, oq.getId());
	}

	@Test
	void getBySubjectAndContent() {
		Question question = this.questionMapper.getQuestionBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, question.getId());
	}

	@Test
	void getBySubjectLike() {
		List<Question> questionList = this.questionMapper.getQuestionBySubjectLike("sbb가 무엇인가요?");
		System.out.println(questionList);
		Question q = questionList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void updateSubject() {
		Optional<Question> oq = this.questionMapper.getQuestionById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionMapper.updateQuestionSubject("수정되었다!", q.getId());
	}

	@Test
	void deleteSubject() {
		assertEquals(2, this.questionMapper.totalCountQuestion());
		Optional<Question> oq = this.questionMapper.getQuestionById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionMapper.deleteQuestionById(q.getId());
		assertEquals(1, this.questionMapper.totalCountQuestion());
	}

	@Test
	void insertAnswer() {
		Optional<Question> oq = this.questionMapper.getQuestionById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestionId(q.getId());
		this.answerMapper.insert(a);
	}

	@Test
	void getAnswerOfQuestion() {
		Optional<Answer> oa = this.answerMapper.getAnswerById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestionId());
	}

	@Transactional
	@Test
	void testJap() {
		Optional<Question> oq = this.questionMapper.getQuestionById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = this.answerMapper.getAnswerListByQuestionId(q.getId());

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@Test
	void dummyTest() {
		final int pageItems = 10;
		final int page = 3;
		int maxId = questionMapper.getMaxQuestionId();

		int pageId = maxId - (page * pageItems);

		List<Question> questionList = questionMapper.getPageList(pageId);

		for (Question q : questionList) {
			System.out.println(q);
		}
	}
}
