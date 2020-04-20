package it.polito.ai.lab2.services;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeamDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public interface TeamServices {



    boolean addCourse(CourseDTO courseDTO);
    Optional<CourseDTO> getCourse(String name);
    List<CourseDTO> getCourses();

    boolean addStudent(StudentDTO studentDTO);
    Optional<StudentDTO> getStudent(String studentId);
    List<StudentDTO> getAllStudents();
    List<StudentDTO> getEnrolledStudents(String courseName);

    boolean addStudentToCourse(String studentId, String courseName);
    void enableCourse(String courseName);
    void disableCourse(String courseName);

    List<Boolean> addAll(List<StudentDTO> students);
    List<Boolean> enrollAll(List<String> studentIds, String courseName);
    List<Boolean> addAndEnroll(Reader r, String courseName) throws IOException;

    List<TeamDTO> getTeamsForStudent(String studentId);
    List<StudentDTO> getMembers(Long teamId);
    List<TeamDTO> getTeamForCourse(String courseName);

}
