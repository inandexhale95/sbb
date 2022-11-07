package com.mysite.sbb.mapper;

import com.mysite.sbb.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(subject, content) VALUES(#{question.subject}, #{question.content})")
    int insert(@Param("question")Question question);

    @Select("SELECT * FROM question")
    @Results(id = "ResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "answerList", column = "id", many = @Many(select = "com.mysite.sbb.mapper.AnswerMapper.getAnswerListByQuestionId"))
    })
    List<Question> getAll();

    @Select("SELECT * FROM question ORDER BY id DESC")
    @ResultMap("ResultMap")
    List<Question> getAllReverse();

    @Select("SELECT * FROM question WHERE id=#{id}")
    @ResultMap(value = "ResultMap")
    Optional<Question> getQuestionById(@Param("id")int id);

    @Select("SELECT * FROM question WHERE subject=#{subject}")
    @ResultMap(value = "ResultMap")
    Question getQuestionBySubject(@Param("subject")String subject);

    @Select("SELECT * FROM question WHERE subject=#{subject} and content=#{content}")
    @ResultMap(value = "ResultMap")
    Question getQuestionBySubjectAndContent(@Param("subject")String subject, @Param("content")String content);

    @Select("SELECT * FROM question WHERE subject LIKE 'sbb%'")
    @ResultMap(value = "ResultMap")
    List<Question> getQuestionBySubjectLike(@Param("subject")String subject);

    @Update("Update question SET subject=#{subject} WHERE id=#{id}")
    void updateQuestionSubject(@Param("subject")String subject, @Param("id") int id);

    @Select("SELECT COUNT(*) FROM question")
    int totalCountQuestion();

    @Delete("DELETE FROM question WHERE id=#{id}")
    void deleteQuestionById(@Param("id")int id);

    @Select("SELECT MAX(id) FROM question")
    int getMaxQuestionId();

    @Select("SELECT * FROM question WHERE id <= #{pageId} AND id > (#{pageId}-10) ORDER BY id DESC")
    List<Question> getPageList(@Param("pageId")int pageId);
}
