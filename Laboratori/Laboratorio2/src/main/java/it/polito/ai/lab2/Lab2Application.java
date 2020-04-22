package it.polito.ai.lab2;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeamDTO;
import it.polito.ai.lab2.entities.Student;
import it.polito.ai.lab2.services.TeamServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    System.out.println("----- Aggiunta corsi: Fisica1, Analisi1. ");
                    CourseDTO c1 = new CourseDTO();
                    c1.setName("Fisica1");
                    c1.setMin(0);
                    c1.setMax(20);
                    teamServices.addCourse(c1);
                    teamServices.enableCourse(c1.getName());

                    CourseDTO c2 = new CourseDTO();
                    c2.setName("Analisi1");
                    c2.setMin(0);
                    c2.setMax(9);
                    teamServices.addCourse(c2);
                    teamServices.enableCourse(c2.getName());

                    System.out.println("-----------------------2-----------------------");
                    System.out.println("----- Stampa (get) un corso che non siste. ");
                    Optional<CourseDTO> courseDTOtoFind = teamServices.getCourse("Algebra");
                    System.out.println(courseDTOtoFind.toString());

                    System.out.println("-----------------------3-----------------------");
                    System.out.println("----- Stampa (get) i corsi esistenti. ");
                    System.out.println(teamServices.getAllCourses().toString());

                    System.out.println("-----------------------4-----------------------");
                    System.out.println("----- Aggiunta studenti: Irene Pulidori, Gabriele Telesca. ");
                    StudentDTO s1 = new StudentDTO();
                    s1.setId("253344");
                    s1.setFirstName("Pulidori");
                    s1.setName("Irene");
                    teamServices.addStudent(s1);

                    StudentDTO s2 = new StudentDTO();
                    s2.setId("293847");
                    s2.setFirstName("Telesca");
                    s2.setName("Gabriele");
                    teamServices.addStudent(s2);

                    System.out.println("------------------------5----------------------");
                    System.out.println("----- Stampa (get) uno studente inesistente. ");
                    System.out.println(teamServices.getStudent("999999"));

                    System.out.println("------------------------6----------------------");
                    System.out.println("----- Stampa (get) gli studenti esistenti. ");
                    System.out.println(teamServices.getAllStudents().toString());

                    System.out.println("------------------------7----------------------");
                    System.out.println("----- Aggiunta di studenti a un corso (uno studente inesistente, un corso inesistente). ");
                    teamServices.addStudentToCourse(s1.getId(), c2.getName());
                    teamServices.addStudentToCourse(s2.getId(), c2.getName());
                    //teamServices.addStudentToCourse("999999", c1.getName());
                    //teamServices.addStudentToCourse(s2.getId(), "algebra");

                    System.out.println("-------------------------8---------------------");
                    System.out.println("----- Stampa (get) gli studenti iscritti a un corso: analisi1, e algebra (inesistente). ");
                    System.out.println(teamServices.getEnrolledStudents(c2.getName()));
                    //System.out.println(teamServices.getEnrolledStudents("algebra"));


                    System.out.println("-------------------------9.1---------------------");
                    System.out.println("----- Prova ad aggiungere una lista di studenti. ");
                    List<StudentDTO> toAdd = new ArrayList<>();
                    System.out.println("----- Studente: Riccarco Rusca. ");
                    StudentDTO s3 = new StudentDTO();
                            s3.setId("256347");
                            s3.setFirstName("Rusca");
                            s3.setName("Riccardo");
                    toAdd.add(s3);
                    System.out.println("----- Studente: Vito Valente. ");
                    StudentDTO s4 = new StudentDTO();
                            s4.setId("456789");
                            s4.setFirstName("Valente");
                            s4.setName("Vito");
                    toAdd.add(s4);
                    teamServices.addAll(toAdd);
                    System.out.println("----- Studente: Riccarco Rusca iscritto ad Analisi1. ");
                    teamServices.addStudentToCourse(s3.getId(), c2.getName());

                    System.out.println("-------------------------9.2---------------------");
                    System.out.println("----- Prova a scrivere una lista di studenti a un corso. ");
                    System.out.println("----- Studente: Riccarco Rusca e Vito Valente iscritti a Fisica1. ");
                    List<String> ids = new ArrayList<>();
                    ids.add(toAdd.get(0).getId());
                    ids.add(toAdd.get(1).getId());
                    teamServices.enrollAll(ids, c1.getName());
                    System.out.println(teamServices.getEnrolledStudents(c1.getName()));


                    System.out.println("-------------------------10---------------------");
                    System.out.println("----- Prova ad aprire un file csv. ");
                    try{
                        Reader r = new FileReader("/Users/matildepulidori/IdeaProjects/Laboratori/Laboratorio2/mydata/data.csv");
                        System.out.println("----- Prova ad aggiungere gli studenti del file al corso di Fisica1. ");
                        teamServices.addAndEnroll(r, c1.getName());
                        System.out.println(teamServices.getEnrolledStudents(c1.getName()));
                    } catch (IOException ioe){
                        throw  new IOException();
                    }


                    System.out.println("-------------------------11---------------------");
                    System.out.println("----- Stampo (get) i corsi a cui è iscritto uno studente esistente (Riccardo Rusca). ");
                    System.out.println(teamServices.getCourses(s3.getId()).toString());

                    System.out.println("-------------------------12---------------------");
                    System.out.println("----- Teams. ");
                    System.out.println("----- Team1: Riccardo Rusca e Vito Valente, corso di Fisica1. ");
                    TeamDTO teamDTO1 = teamServices.proposeTeam(c1.getName(),"uno", ids );
                    System.out.println(teamDTO1.toString());
                    List<StudentDTO> studentsOfc1 = new ArrayList<>(teamServices.getEnrolledStudents(c1.getName()));

                    System.out.println("----- Team2: Tre studenti dal file csv, corso di Fisica1. ");
                    List<String> newGroup = new ArrayList<>();
                    newGroup.add(studentsOfc1.get(3).getId());
                    newGroup.add(studentsOfc1.get(4).getId());
                    //newGroup.add(studentsOfc1.get(5).getId());
                    TeamDTO teamDTO2 = teamServices.proposeTeam(c1.getName(),"due", newGroup );
                    System.out.println(teamDTO2.toString());

                    System.out.println("----- Teams per il corso di Fisica1. ");
                    System.out.println(teamServices.getTeamForCourse(c1.getName()));

                    System.out.println("-------------------------13---------------------");
                    System.out.println("----- Studenti n un team per il corso di Fisica1. ");
                    System.out.println(teamServices.getStudentsInTeam(c1.getName()));

                    System.out.println("-------------------------14---------------------");
                    System.out.println("----- Studenti non in un team per il corso di Fisica1. ");
                    System.out.println(teamServices.getStudentsNotInTeam(c1.getName()));



                } catch (IllegalStateException | IOException ise){
                    throw new IllegalStateException("Command Line Runner");
                }

            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

}
