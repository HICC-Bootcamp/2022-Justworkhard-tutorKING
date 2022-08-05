package com.hicc.tutorking.controller;

import com.hicc.tutorking.dto.ConnectionDto;
import com.hicc.tutorking.dto.StudentInfoDto;
import com.hicc.tutorking.entity.Connection;
import com.hicc.tutorking.entity.Student;
import com.hicc.tutorking.entity.Teacher;
import com.hicc.tutorking.repository.TeacherRepository;
import com.hicc.tutorking.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/students")
@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final TeacherRepository teacherRepository;

    @GetMapping(value = "/info")
    public String studentForm(Model model) {
        model.addAttribute("studentInfoDto", new StudentInfoDto());
        return "student/student_info";
    }

    @PostMapping(value = "/info")
    public String newStudentInfo(@Valid StudentInfoDto studentInfoDto, BindingResult bindingResult,
                                 Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "student/student_info";
        }

        try {
            String studentEmail = principal.getName();
            Student student = Student.createStudent(studentInfoDto, studentEmail);
            studentService.saveStudent(student);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "student/student_info";
        }


        return "redirect:/students/connections";
    }

    @GetMapping(value = "/connections") //TODO:학생의 메인페이지, 버튼을 눌러서 매칭으로 들어갈건지 요청한 매칭 확인 부분으로
    // 들어갈 건지는 프론트엔드에서 구현함(프론트)
    public String studentMain() {
        studentService.resetHashtag(); //TODO:이거 좀 하.. 일단 메인 열릴때마다 해시태그 리셋되게 함.. 로그아웃되면 리셋되게 하고 싶음(백엔드)
        return "student/student_main";
    }

    @GetMapping(value = {"/connections/matching"}) // 학생의 매칭 부분으로 들어감
    public String studentMatch(Principal principal, Model model) {
        String studentEmail = principal.getName();
        studentService.suggest(studentEmail);

        List<Teacher> teachers = studentService.getTeacherList();
        model.addAttribute("teachers", teachers);//TODO:프론트엔드에 학생 인포에 맞는 선생님 리스트가 넘어감

        model.addAttribute("connectionDto", new ConnectionDto());//TODO:프론트엔드가 학생이 고른 선생님의 이메일을 백엔드로 줌
        return "student/student_matching";
    }

    @PostMapping(value = "/connections/matching")
    public String studentConnection(Principal principal, ConnectionDto connectionDto,
                                    BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "student/student_matching";
        }

        String studentEmail = principal.getName();

        try {
            Connection connection = Connection.createConnection(studentEmail, connectionDto);
            studentService.saveConnection(connection);
            studentService.setPhoneNumbers(studentEmail, connectionDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "student/student_matching";
        }

        return "redirect:/students/connections"; //student 페이지 메인으로 돌아간다
    }

    @GetMapping(value = "/connections/check")
    public String studentConnectionCheck(Principal principal, Model model) {
        String studentEmail = principal.getName();
        Connection connection= studentService.checkConnection(studentEmail);
        Student studentInfo=studentService.getStudentInfo(studentEmail);
        Teacher teacherInfo=studentService.getTeacherInfo(studentEmail);
        model.addAttribute("connection", connection);
        model.addAttribute("studentInfo",studentInfo);
        model.addAttribute("teacherInfo",teacherInfo);

        return "/student/student_waiting";
    }


}



