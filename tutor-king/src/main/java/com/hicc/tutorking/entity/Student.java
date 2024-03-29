package com.hicc.tutorking.entity;

import com.hicc.tutorking.dto.StudentInfoDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private String studentEmail;

    private String area; //사는 곳
    private String admission; //입학일
    private String subject;
    private String teacherStyle; // 원하는 선생님의 성향
    private Integer money; //지불하기를 원하는 금액

    public static Student createStudent(StudentInfoDto studentInfoDto,String studentEmail,Account account) {
        Student student = new Student();
        student.setArea(studentInfoDto.getArea());
        student.setStudentEmail(studentEmail);
        student.setAdmission(studentInfoDto.getAdmission());
        student.setSubject(studentInfoDto.getSubject());
        student.setTeacherStyle(studentInfoDto.getTeacherStyle());
        student.setMoney(studentInfoDto.getMoney());
        student.setAccount(account);
        return student;

    }
}