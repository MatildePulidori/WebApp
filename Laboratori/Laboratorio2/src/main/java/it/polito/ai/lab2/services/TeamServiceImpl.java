package it.polito.ai.lab2.services;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeamDTO;
import it.polito.ai.lab2.entities.Course;
import it.polito.ai.lab2.entities.Student;
import it.polito.ai.lab2.entities.Team;
import it.polito.ai.lab2.repositories.CourseRepository;
import it.polito.ai.lab2.repositories.StudentRepository;
import it.polito.ai.lab2.repositories.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamServices {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeamRepository teamRepository;




    @Override
    public boolean addCourse(CourseDTO courseDTO) {

        if(courseRepository.existsById(courseDTO.getName())==false){
            Course c = new Course();
            c.setName(courseDTO.getName());
            c.setMin(2);
            c.setMax(20);
            courseRepository.save(c);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CourseDTO> getCourse(String name) {
        Optional<CourseDTO> courseDTO = courseRepository.findById(name)
                .map(c-> modelMapper.map(c, CourseDTO.class));
        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(c->modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO) {

        if(studentRepository.existsById(studentDTO.getId())==false){
            Student s = new Student();
            s.setId(studentDTO.getId());
            s.setFirstName(studentDTO.getFirstName());
            s.setName(studentDTO.getName());
            studentRepository.save(modelMapper.map(studentDTO, Student.class));
            return true;
        }
        return false;
    }

    @Override
    public Optional<StudentDTO> getStudent(String studentId) {
        Optional<StudentDTO> studentDTO = studentRepository.findById(studentId)
                .map(s->modelMapper.map(s, StudentDTO.class));
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(s->modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getEnrolledStudents(String courseName) {

        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }

        List<Student> students = courseRepository.getOne(courseName).getStudents();
        List<StudentDTO> enrolled = new ArrayList<>();
        for (Student s : students) {
            enrolled.add(modelMapper.map(s, StudentDTO.class));
        }
        return enrolled;

    }

    @Override
    public boolean addStudentToCourse(String studentId, String courseName) {

        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }

        if (!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException();
        }

        Course c = courseRepository.getOne(courseName);
        if (!c.isEnabled()) return false;

        Student s = studentRepository.getOne(studentId);
        c.addStudent(s);
        return true;
    }

    @Override
    public void enableCourse(String courseName) {

        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }
        courseRepository.getOne(courseName).setEnabled(true);

    }

    @Override
    public void disableCourse(String courseName) {

        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }
        courseRepository.getOne(courseName).setEnabled(false);

    }


    @Override
    public List<Boolean> addAll(List<StudentDTO> students) {
        List<Boolean> added = new ArrayList<>();
        for( StudentDTO s: students) {
            if (!studentRepository.existsById(s.getId())) {
                Student newStudent = new Student();
                newStudent.setId(s.getId());
                newStudent.setFirstName(s.getFirstName());
                newStudent.setName(s.getName());
                studentRepository.save(newStudent);
                added.add(true);
            } else {
                added.add(false);
            }
        }

        return added;
    }

    @Override
    public List<Boolean> enrollAll(List<String> studentIds, String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException();
        }
        List<Boolean> enrolled = new ArrayList<>();
        for (String id : studentIds) {

            boolean added = false;
            if (!studentRepository.existsById(id)) {
                throw new StudentNotFoundException();
            }
            added = this.addStudentToCourse(id, courseName);

            enrolled.add(added);
        }
        return enrolled;
    }

    @Override
    public List<Boolean> addAndEnroll(Reader r, String courseName) throws IOException {

        if(!courseRepository.existsById(courseName)){
            throw  new CourseNotFoundException();
        }
        List<Boolean> addenroll = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader( r);
        String row = "";
        while( (row = bufReader.readLine()) != null){
            String[] student = row.split(",");

            StudentDTO newStudent = new StudentDTO();
            newStudent.setId(student[0]);
            newStudent.setName(student[1]);
            newStudent.setFirstName(student[2]);


            this.addStudent(newStudent);
            boolean res = this.addStudentToCourse(newStudent.getId(), courseName);
            addenroll.add(res);
        }

        return addenroll;
    }

    @Override
    public List<CourseDTO> getCourses(String studentId) {
        if (!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException();
        }
        List<CourseDTO> coursesOfStudent = new ArrayList<>();
        for (Course course : studentRepository.getOne(studentId).getCourses()){
            coursesOfStudent.add(modelMapper.map(course, CourseDTO.class));
        }
        return coursesOfStudent;
    }


    @Override
    public List<TeamDTO> getTeamsForStudent(String studentId) {
        if (!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException();
        }
        List<TeamDTO> teams = new ArrayList<>();
        for(Team t : teamRepository.getTeamByMembers(studentRepository.getOne(studentId))){
            teams.add(modelMapper.map(t, TeamDTO.class));
        }


        /*
        for (Team t : studentRepository.getOne(studentId).getTeams()) {
               teams.add(modelMapper.map(t, TeamDTO.class));
        }
        */
        return teams;
    }

    @Override
    public List<StudentDTO> getMembers(Long teamId) {
        if (!teamRepository.existsById(teamId)){
            throw new TeamNotFoundException();
        }

        List<StudentDTO> members = new ArrayList<>();
        for (Student s : teamRepository.getOne(teamId).getMembers()){
            members.add(modelMapper.map(s, StudentDTO.class));
        }
        return members;
    }

    @Override
    public List<TeamDTO> getTeamForCourse(String courseName) {
        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }
        List<TeamDTO> teams = new ArrayList<>();
        for(Team t :  teamRepository.getTeamByCourse(courseRepository.getOne(courseName))){
            teams.add(modelMapper.map(t, TeamDTO.class));
        }
        return teams;
    }

    @Override
    public TeamDTO proposeTeam(String courseName, String name, List<String> memeberIds) {

        // .. Se il corso non esiste
        if (!courseRepository.existsById(courseName)){
            throw new CourseNotFoundException();
        }

        Course currCourse = new Course();
        currCourse = courseRepository.getOne(courseName);

        // .. Se il corso non è abilitato
        if (!currCourse.isEnabled()){
            throw new TeamServiceException();
        }


        // .. Rispetto cardinalità del corso
        if ( memeberIds.size() >= currCourse.getMin())
        { System.out.println("course min: "+currCourse.getMin());
        }
        if( memeberIds.size() < currCourse.getMax() ){
            System.out.println("course max: "+currCourse.getMax());
        }

        // .. Controllo duplicati

        for (int i = 0; i<memeberIds.size(); i++){
            for ( int j=i+1; j<memeberIds.size(); j++){
                if (memeberIds.get(i).compareTo(memeberIds.get(j)) ==0) throw  new TeamServiceException();
            }
        }


        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setCourse(currCourse);


        for (String memeberId : memeberIds){

            // .. Se lo studente non esiste
            if (!studentRepository.existsById(memeberId)){
                throw new StudentNotFoundException();
            }

            Student currStudent = studentRepository.getOne(memeberId);

            // .. Se lo studente non è iscritto al corso
            if (!currCourse.contains(currStudent)){
                throw new TeamServiceException();
            }
            if (!currStudent.contains(currCourse)){
                throw new TeamServiceException();
            }

            // .. Se lo studente è in un altro gruppo
            for(Team teamOfTheCourse : currCourse.getTeams()){
                if (teamOfTheCourse.contains(currStudent)){
                    throw new TeamServiceException();
                }
            }

            newTeam.addMember(currStudent); // Questo metodo chiama anche per lo studente l'addTeam.
        }
        teamRepository.save(newTeam);
        return modelMapper.map(newTeam, TeamDTO.class);

    }

    @Override
    public List<Student> getStudentsInTeam(String courseName) {
        if (!courseRepository.existsById(courseName)){
            throw  new CourseNotFoundException();
        }
        return courseRepository.getStudentsInTeams(courseName);
    }

    @Override
    public List<Student> getStudentsNotInTeam(String courseName) {
        if (!courseRepository.existsById(courseName)){
            throw  new CourseNotFoundException();
        }
        return courseRepository.getStudentsNotInTeams(courseName);
    }
}
