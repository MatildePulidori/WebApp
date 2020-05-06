package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public class ModelHelper {

    public static CourseDTO enrich(CourseDTO courseDTO){

        courseDTO.add(linkTo(methodOn(CourseController.class).getOne(courseDTO.getName())).withSelfRel());
        courseDTO.add(linkTo(methodOn(CourseController.class).enrolledStudents(courseDTO.getName())).withRel("enrolled"));
        return courseDTO;
    }

    public static StudentDTO enrich(StudentDTO studentDTO){

        studentDTO.add(linkTo(methodOn(StudentController.class).getOne(studentDTO.getId())).withSelfRel());
        return studentDTO;
    }



}
