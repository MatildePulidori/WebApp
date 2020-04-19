package it.polito.ai.lab2;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.entities.Course;
import it.polito.ai.lab2.repositories.CourseRepository;
import it.polito.ai.lab2.repositories.StudentRepository;
import it.polito.ai.lab2.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class Lab2Application {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner runner(TeamServices teamServices){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws RuntimeException {

                try {


                    System.out.println("----------------------1------------------------");

                    CourseDTO c = new CourseDTO();
                    c.setName("fisica1");
                    c.setMin(2);
                    c.setMax(20);
                    teamServices.addCourse(c);
                    teamServices.enableCourse(c.getName());

                    System.out.println("-----------------------2-----------------------");
                    Optional<CourseDTO> courseDTOtoFind = teamServices.getCourse("algebra");
                    System.out.println(courseDTOtoFind.toString());

                    System.out.println("-----------------------3-----------------------");


                    StudentDTO s = new StudentDTO();
                    s.setId("253344");
                    s.setFirstName("pulidori");
                    s.setName("irene");
                    teamServices.addStudent(s);

                    System.out.println("------------------------4----------------------");
                    System.out.println(teamServices.getStudent("123456").toString());
                    System.out.println("------------------------5----------------------");

                    teamServices.addStudentToCourse("123456", c.getName());

                    System.out.println("-------------------------6---------------------");
                    System.out.println(teamServices.getEnrolledStudents("algebra"));
                    System.out.println("-------------------------7---------------------");

                } catch (IllegalStateException ise){
                    throw new IllegalStateException("Command Line Runner");
                }

            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

}
