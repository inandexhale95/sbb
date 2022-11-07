package com.mysite.sbb.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForm {

    @NotEmpty(message = "제목은 필수입니다.")
    @Size(max = 100)
    private String subject;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}
