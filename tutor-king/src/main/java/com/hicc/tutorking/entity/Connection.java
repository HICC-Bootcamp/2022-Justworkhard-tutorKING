package com.hicc.tutorking.entity;

import com.hicc.tutorking.dto.ConnectionDto;
import com.hicc.tutorking.dto.TeacherInfoDto;
import com.hicc.tutorking.dto.TeacherReplyDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="connection")
@Getter
@Setter
@ToString
public class Connection {
    @Id
    @Column(name="connection_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String studentEmail;
    private String teacherEmail;
    private Boolean teacherReply;

    public static Connection createConnection(String studentEmail, ConnectionDto connectionDto) {
        Connection connection = new Connection();
        connection.setStudentEmail(studentEmail);
        connection.setTeacherEmail(connectionDto.getTeacherEmail());
        connection.setTeacherReply(null);

        return connection;
    }


}
