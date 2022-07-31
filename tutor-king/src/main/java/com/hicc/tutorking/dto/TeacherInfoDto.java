package com.hicc.tutorking.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class TeacherInfoDto {


    @NotBlank(message = "거주 지역은 필수 입력 값입니다.")
    private String area;

    @PositiveOrZero(message = "숫자를 입력해주세요. ")
    @NotBlank(message = "생년월일은 필수 입력 값입니다.")
    private int age;

    @NotBlank(message = "수업 성향은 필수 입력 값입니다.")
    private String style;

    @NotBlank(message = "과외할 과목은 필수 입력 값입니다.")
    private String subject;

    @NotBlank(message = "과외생의 희망 학년은 필수 입력 값입니다. ")
    private String target;

    @NotBlank(message = "자신의 대학은 필수 입력 값입니다. ")
    private String university;

    @NotBlank(message = "자신의 전공은 필수 입력 값입니다. ")
    private String major;

    @PositiveOrZero(message = "숫자를 입력해주세요. ")
    @NotBlank(message = "희망 과외비는 필수 입력 값입니다.")
    private String wage;

    @Lob
    @Length(min=0, max=100, message = "100자 이내로 입력해주세요.")
    private String experience;
}