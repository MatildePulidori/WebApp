package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.services.exceptions.CourseNotFoundException;
import it.polito.ai.lab3.services.NotificationService;
import it.polito.ai.lab3.services.exceptions.StudentNotFoundException;
import it.polito.ai.lab3.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/API/courses")
public class CourseController {


    @Autowired
    private TeamServices teamServices;

    @Autowired
    private NotificationService notificationService;


    @GetMapping({"", "/"})
    List<CourseDTO> all(){
        List<CourseDTO> result = new ArrayList<CourseDTO>();
        teamServices.getAllCourses().stream().forEach(courseDTO ->
                result.add(ModelHelper.enrich(courseDTO))  );
        return result;
    }

    @GetMapping("/{name}")
    CourseDTO getOne(@PathVariable String name) {
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        return ModelHelper.enrich(optionalCourseDTO.get());
    }

    @GetMapping("/{name}/enrolled")
    List<StudentDTO> enrolledStudents(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        List<StudentDTO> result = new ArrayList<>();
        teamServices.getEnrolledStudents(name).stream().forEach(
                studentDTO -> result.add(ModelHelper.enrich(studentDTO)));
        return result;
    }

    @GetMapping("/{name}/teams")
    List<TeamDTO> getTeams(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        List<TeamDTO> result = new ArrayList<>();
        teamServices.getTeamForCourse(name)
                .stream()
                .forEach(
                        teamDTO -> result.add(ModelHelper.enrich(teamDTO)));
        return result;
    }


    @GetMapping("/{name}/availableStudents")
    List<StudentDTO> getAvailableStudents(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        List<StudentDTO> result = new ArrayList<>();
        teamServices.getAvailableStudents(name)
                .stream()
                .forEach(
                        studentDTO -> result.add(ModelHelper.enrich(studentDTO)));
        return result;
    }

    @GetMapping("/{name}/studentsInTeam")
    List<StudentDTO> getStudentsInTema(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        List<StudentDTO> result = new ArrayList<>();
        teamServices.getStudentsInTeam(name)
                .stream()
                .forEach(
                        studentDTO -> result.add(ModelHelper.enrich(studentDTO)));
        return result;
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    CourseDTO addCourse(@RequestBody CourseDTO courseDTO){
        if (!teamServices.addCourse(courseDTO)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, courseDTO.getName());
        }
        return ModelHelper.enrich(courseDTO);
    }


    @PostMapping("/{name}/enrollOne")
    @ResponseStatus(HttpStatus.CREATED)
    void entrollOne(@PathVariable String name, @RequestBody Map<String, String> input ){
        try{
            if(input.containsKey("id")) {
                teamServices.addStudentToCourse(input.get("id"), name);
            }
        }catch (StudentNotFoundException sfe){
            if(input.containsKey("id")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, input.get("id"));
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "<NO_STUDENT_ID>");
        }catch ( CourseNotFoundException cfe){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, name);
        }

    }

    @PostMapping("/{name}/enrollMany")
    @ResponseStatus(HttpStatus.CREATED)
    List<Boolean> enrollStudents(@PathVariable String name, @RequestParam("file") MultipartFile file) {
        try {
            if (file.getContentType().compareTo("text/csv") != 0) {
                throw new HttpClientErrorException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, file.getContentType());
            }
            Reader r = new InputStreamReader( file.getInputStream());
            return teamServices.addAndEnroll(r, name);
        } catch (IOException ioe){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }catch (StudentNotFoundException sfe){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch ( CourseNotFoundException cfe){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, name);
        }

    }


    @PostMapping("/{name}/proposeTeam/")
    @ResponseStatus(HttpStatus.CREATED)
    TeamDTO proposeTeam(@PathVariable String name, @PathVariable String teamId, @RequestBody List<Map<String, String>> input) {
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }

        List<String> studs = new ArrayList<>();

        input.stream()
                .filter( map -> map.containsKey("id"))
                .forEach( m  -> studs.add( m.get("id")) );

        TeamDTO teamDTO= teamServices.proposeTeam(name, input.get(0).get("team"), studs);
        notificationService.notifyTeam(teamDTO,studs);

        return teamDTO;
    }

    @PutMapping("/{name}/enable")
    @ResponseStatus(HttpStatus.OK)
    public void enableCourse(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        teamServices.enableCourse(name);
        ModelHelper.enrich(optionalCourseDTO.get());
    }


    @PostMapping("/{name}/disable")
    @ResponseStatus(HttpStatus.OK)
    public void diableCourse(@PathVariable String name){
        Optional<CourseDTO> optionalCourseDTO = teamServices.getCourse(name);
        if (!optionalCourseDTO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        teamServices.disableCourse(name);
        ModelHelper.enrich(optionalCourseDTO.get());
    }
}

