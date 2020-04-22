package it.polito.ai.lab2.repositories;

import it.polito.ai.lab2.entities.Course;
import it.polito.ai.lab2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("SELECT s FROM Student s INNER JOIN s.teams t INNER JOIN t.course c WHERE c.name=:courseName")
    List<Student> getStudentsInTeams(String courseName);

    @Query("SELECT s FROM Student as s, Course as c " +
            "WHERE c.name=:courseName " +
            "AND c IN ( SELECT c FROM s.courses ) " +
            "AND s  NOT IN" +
            "(SELECT s2 FROM Student s2 INNER JOIN s2.teams t INNER JOIN t.course c2 WHERE c2.name=:courseName) ")
    List<Student> getStudentsNotInTeams(String courseName);





}
