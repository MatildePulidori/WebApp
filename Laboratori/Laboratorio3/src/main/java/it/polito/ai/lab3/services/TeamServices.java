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

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    boolean addCourse(CourseDTO courseDTO);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    Optional<CourseDTO> getCourse(String name);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    List<CourseDTO> getAllCourses();

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    boolean addStudent(StudentDTO studentDTO);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<StudentDTO> getEnrolledStudents(String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    boolean addStudentToCourse(String studentId, String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void enableCourse(String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void disableCourse(String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<Boolean> addAll(List<StudentDTO> students);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<Boolean> enrollAll(List<String> studentIds, String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<Boolean> addAndEnroll(Reader r, String courseName) throws IOException;

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    CourseDTO getCourse(Long teamId);

    /* ----------------------------------------- STUDENT ----------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')"+ "&& authentication.principal.username==#studentId")
    Optional<StudentDTO> getStudent(String studentId);

    @PreAuthorize("hasRole('ROLE_STUDENT')"+ "&& authentication.principal.username==#studentId")
    List<CourseDTO> getCourses(String studentId);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    List<StudentDTO> getAllStudents();

    @PreAuthorize("hasRole('ROLE_STUDENT')"+ "&& authentication.principal.username==#studentId")
    List<TeamDTO> getTeamsForStudent(String studentId);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    List<StudentDTO> getMembers(Long teamId);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    List<TeamDTO> getTeamForCourse(String courseName);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    TeamDTO proposeTeam(String courseId, String name, List<String> memeberIds);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    void setTeamStatus(Long teamId);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    void evictTeam(Long teamId);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    List<StudentDTO> getStudentsInTeam(String courseName);

    @PreAuthorize("hasRole('ROLE_TEACHER')"+ "|| hasRole('ROLE_STUDENT')")
    List<StudentDTO> getAvailableStudents(String courseName);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    TeamDTO getTeam(Long teamId);
}
