package com.mysite.sbb.mapper;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AnswerMapper {

    @Insert("INSERT INTO answer(content, question_id) VALUES(#{answer.content}, #{answer.questionId})")
    int insert(@Param("answer") Answer answer);

    @Select("SELECT * FROM answer")
    @Results(id = "AnswerMap", value = {
            @Result(property = "content", column = "content"),
            @Result(property = "questionId", column = "question_id"),
    })
    List<Answer> getAnswerList();

    @Select("SELECT * FROM answer WHERE id=#{id}")
    @ResultMap(value = "AnswerMap")
    Optional<Answer> getAnswerById(@Param("id")int id);

    @Select("SELECT * FROM answer WHERE question_id=#{questionId}")
    @ResultMap(value = "AnswerMap")
    List<Answer> getAnswerListByQuestionId(@Param("questionId")int questionId);
}
