package it.polito.ai.lab3.services;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public interface TeamServices {


    /* ----------------------------------------- TEACHER ----------------------------------------- */

    @PreAuthorize("hasRole('TEACHER')")
    boolean addCourse(CourseDTO courseDTO);

    @PreAuthorize("hasRole('TEACHER')")
    Optional<CourseDTO> getCourse(String name);

    @PreAuthorize("hasRole('TEACHER')")
    List<CourseDTO> getAllCourses();

    @PreAuthorize("hasRole('TEACHER')")
    boolean addStudent(StudentDTO studentDTO);

    @PreAuthorize("hasRole('TEACHER')")
    Optional<StudentDTO> getStudent(String studentId);

    @PreAuthorize("hasRole('TEACHER')")
    List<StudentDTO> getAllStudents();

    @PreAuthorize("hasRole('TEACHER')")
    List<StudentDTO> getEnrolledStudents(String courseName);

    @PreAuthorize("hasRole('TEACHER')")
    boolean addStudentToCourse(String studentId, String courseName);

    @PreAuthorize("hasRole('TEACHER')")
    void enableCourse(String courseName);

    @PreAuthorize("hasRole('TEACHER')")
    void disableCourse(String courseName);

    @PreAuthorize("hasRole('TEACHER')")
    List<Boolean> addAll(List<StudentDTO> students);

    @PreAuthorize("hasRole('TEACHER')")
    List<Boolean> enrollAll(List<String> studentIds, String courseName);

    @PreAuthorize("hasRole('TEACHER')")
    List<Boolean> addAndEnroll(Reader r, String courseName) throws IOException;

    @PreAuthorize("hasRole('TEACHER')")
    List<CourseDTO> getCourses(String studentId);



    /* ----------------------------------------- STUDENT ----------------------------------------- */
    @PreAuthorize("hasRole('STUDENT')")
    List<TeamDTO> getTeamsForStudent(String studentId);

    @PreAuthorize("hasRole('STUDENT')")
    List<StudentDTO> getMembers(Long teamId);

    @PreAuthorize("hasRole('STUDENT')")
    List<TeamDTO> getTeamForCourse(String courseName);

    @PreAuthorize("hasRole('STUDENT')")
    TeamDTO proposeTeam(String courseId, String name, List<String> memeberIds);

    @PreAuthorize("hasRole('STUDENT')")
    void setTeamStatus(Long teamId);

    @PreAuthorize("hasRole('STUDENT')")
    void evictTeam(Long teamId);

    @PreAuthorize("hasRole('STUDENT')")
    List<StudentDTO> getStudentsInTeam(String courseName);

    @PreAuthorize("hasRole('STUDENT')")
    List<StudentDTO> getAvailableStudents(String courseName);

}
