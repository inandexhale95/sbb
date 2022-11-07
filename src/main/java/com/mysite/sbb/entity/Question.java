package com.mysite.sbb.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private int id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private List<Answer> answerList;
}
