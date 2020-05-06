package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
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

    @GetMapping()
    List<StudentDTO> all(){
        List<StudentDTO> result = new ArrayList<>();
        teamServices.getAllStudents().stream().forEach(
                studentDTO -> result.add(ModelHelper.enrich(studentDTO)));
        return result;
    }

    @GetMapping("/{id}")
    StudentDTO getOne(@PathVariable String id) {
        Optional<StudentDTO> optionalStudentDTO = teamServices.getStudent(id);
        if (!optionalStudentDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, id);
        }
        return ModelHelper.enrich(optionalStudentDTO.get());
    }

    @PostMapping({"", "/"})
    StudentDTO addStudent(@RequestBody StudentDTO studentDTO){
        if (!teamServices.addStudent(studentDTO)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, studentDTO.getId());
        }
        return ModelHelper.enrich(studentDTO);
    }


}
