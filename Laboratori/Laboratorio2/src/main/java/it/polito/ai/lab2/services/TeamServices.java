package it.polito.ai.lab2.services;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeamDTO;
import it.polito.ai.lab2.entities.Student;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public interface TeamServices {


    /* 10 */
    boolean addCourse(CourseDTO courseDTO);
    Optional<CourseDTO> getCourse(String name);
    List<CourseDTO> getAllCourses();

    boolean addStudent(StudentDTO studentDTO);
    Optional<StudentDTO> getStudent(String studentId);
    List<StudentDTO> getAllStudents();
    List<StudentDTO> getEnrolledStudents(String courseName);

    boolean addStudentToCourse(String studentId, String courseName);
    void enableCourse(String courseName);
    void disableCourse(String courseName);

    /* 12 */
    List<Boolean> addAll(List<StudentDTO> students);
    List<Boolean> enrollAll(List<String> studentIds, String courseName);
    List<Boolean> addAndEnroll(Reader r, String courseName) throws IOException;

    /* 13 */
    List<CourseDTO> getCourses(String studentId);

    /* 15 */
    List<TeamDTO> getTeamsForStudent(String studentId);
    List<StudentDTO> getMembers(Long teamId);
    List<TeamDTO> getTeamForCourse(String courseName);

    /* 16 */
    TeamDTO proposeTeam(String courseId, String name, List<String> memeberIds );

    /* 18 */
    List<Student> getStudentsInTeam(String courseName);
    List<Student> getStudentsAvailable(String courseName);

}
