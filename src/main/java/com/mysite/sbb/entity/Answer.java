package com.mysite.sbb.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private int id;
    private String content;
    private LocalDateTime createDate;
    private int questionId;
}
