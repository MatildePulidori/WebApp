package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.entities.Course;
import it.polito.ai.lab3.entities.Student;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public class ModelHelper {

    public static CourseDTO enrich(CourseDTO courseDTO){

        courseDTO.add(linkTo(methodOn(CourseController.class).getOne(courseDTO.getName())).withSelfRel());
        courseDTO.add(linkTo(methodOn(CourseController.class).enrolledStudents(courseDTO.getName())).withRel("enrolled"));
        return courseDTO;
    }

    public static StudentDTO enrich(StudentDTO studentDTO){

        studentDTO.add(linkTo(methodOn(StudentController.class).getOne(studentDTO.getId())).withSelfRel());
        studentDTO.add(linkTo(methodOn(StudentController.class).getCoursesForStudent(studentDTO.getId())).withRel("courses"));
        studentDTO.add(linkTo(methodOn(StudentController.class).getTeamsForStudent(studentDTO.getId())).withRel("teams"));
        return studentDTO;
    }


    public static TeamDTO enrich(TeamDTO teamDTO) {
        teamDTO.add(linkTo(methodOn(TeamController.class).getOne(teamDTO.getId())).withSelfRel());
        teamDTO.add(linkTo(methodOn(TeamController.class).getCourse(teamDTO.getId())).withRel("course"));


        return teamDTO;
    }


}
