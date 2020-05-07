package it.polito.ai.lab3.repositories;

import it.polito.ai.lab3.entities.Course;
import it.polito.ai.lab3.entities.Student;
import it.polito.ai.lab3.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}

