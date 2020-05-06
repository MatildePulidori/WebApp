package it.polito.ai.lab3.repositories;

import it.polito.ai.lab3.entities.Course;
import it.polito.ai.lab3.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> getTeamByCourse(Course course);


}
