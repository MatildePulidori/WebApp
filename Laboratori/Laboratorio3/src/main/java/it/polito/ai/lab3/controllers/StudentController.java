package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.entities.Student;
import it.polito.ai.lab3.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API/students")
public class StudentController {

    @Autowired
    private TeamServices teamServices;

    @GetMapping({"", "/"})
    public List<StudentDTO> all(){
        List<StudentDTO> result = new ArrayList<>();
        teamServices.getAllStudents().stream().forEach(
                studentDTO -> result.add(ModelHelper.enrich(studentDTO)));
        return result;
    }

    @GetMapping("/{id}")
    public StudentDTO getOne(@PathVariable String id) {
        Optional<StudentDTO> optionalStudentDTO = teamServices.getStudent(id);
        if (!optionalStudentDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, id);
        }
        return ModelHelper.enrich(optionalStudentDTO.get());
    }


    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO){
        if (!teamServices.addStudent(studentDTO)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, studentDTO.getId());
        }
        return ModelHelper.enrich(studentDTO);
    }


    @GetMapping("/{id}/courses")
    public List<CourseDTO> getCoursesForStudent(@PathVariable String id){
        Optional<StudentDTO> optionalStudentDTO = teamServices.getStudent(id);
        if (!optionalStudentDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, id);
        }

        List<CourseDTO> result = new ArrayList<>();
        teamServices.getCourses(id)
                .stream()
                .forEach(
                        courseDTO -> result.add(ModelHelper.enrich(courseDTO)));

        return result;
    }


    @GetMapping("/{id}/teams")
    public List<TeamDTO> getTeamsForStudent(@PathVariable String id){
        Optional<StudentDTO> optionalStudentDTO = teamServices.getStudent(id);
        if (!optionalStudentDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, id);
        }

        List<TeamDTO> result = new ArrayList<>();
        teamServices.getTeamsForStudent(id)
                .stream()
                .forEach(
                        teamDTO -> result.add(ModelHelper.enrich(teamDTO)));

        return result;
    }
}
