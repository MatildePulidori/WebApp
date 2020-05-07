package it.polito.ai.lab3.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.StudentDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.entities.Course;
import it.polito.ai.lab3.entities.Student;
import it.polito.ai.lab3.entities.Team;
import it.polito.ai.lab3.repositories.CourseRepository;
import it.polito.ai.lab3.repositories.StudentRepository;
import it.polito.ai.lab3.repositories.TeamRepository;
import it.polito.ai.lab3.services.exceptions.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
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

        if (courseRepository.existsById(courseDTO.getName()) == false) {
            courseRepository.save(this.modelMapper.map(courseDTO, Course.class));
            return true;
        }
        return false;
    }

    @Override
    public Optional<CourseDTO> getCourse(String name) {
        Optional<CourseDTO> courseDTO = courseRepository.findById(name)
                .map(c -> this.modelMapper.map(c, CourseDTO.class));
        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO) {
        if (studentRepository.existsById(studentDTO.getId()) == false) {
            studentRepository.save(this.modelMapper.map(studentDTO, Student.class));
            return true;
        }
        return false;
    }


    @Override
    public Optional<StudentDTO> getStudent(String studentId) {
        Optional<StudentDTO> studentDTO = studentRepository.findById(studentId)
                .map(s -> modelMapper.map(s, StudentDTO.class));
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getEnrolledStudents(String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }

        List<StudentDTO> enrolled = new ArrayList<StudentDTO>(
                courseRepository.getOne(courseName).getStudents()
                        .stream()
                        .map(s -> this.modelMapper.map(s, StudentDTO.class))
                        .collect(Collectors.toList()));

        return enrolled;

    }

    @Override
public boolean addStudentToCourse(String studentId, String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Studente non trovato.");
        }

        Course c = courseRepository.getOne(courseName);
        if (!c.isEnabled()) throw new CourseNotEnabledException("Corso non abilitato.");


        Student s = studentRepository.getOne(studentId);

        List<StudentDTO> enrolled = new ArrayList<>(this.getEnrolledStudents(c.getName()));
        for (StudentDTO enr : enrolled) {
            if (enr.getId().compareTo(studentId) == 0) {
                return false;
            }
        }
        s.addCourse(c);
        return true;
    }

    @Override
    public void enableCourse(String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        courseRepository.getOne(courseName).setEnabled(true);

    }

    @Override
    public void disableCourse(String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        courseRepository.getOne(courseName).setEnabled(false);

    }


    @Override
    public List<Boolean> addAll(List<StudentDTO> students) {
        List<Boolean> added = new ArrayList<>();
        students.stream().forEach(s -> added.add(this.addStudent(s)));
        return added;
    }

    @Override
    public List<Boolean> enrollAll(List<String> studentIds, String courseName) {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        if (!courseRepository.findById(courseName).isPresent()) {
            throw new CourseNotEnabledException("Corso non abilitato.");
        }

        List<Boolean> enrolled = new ArrayList<>();
        studentIds.stream().forEach(id -> enrolled.add(this.addStudentToCourse(id, courseName)));

        return enrolled;
    }

    @Override
    public List<Boolean> addAndEnroll(Reader reader, String courseName) throws IOException {

        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        if (!courseRepository.findById(courseName).isPresent()) {
            throw new CourseNotEnabledException("Corso non abilitato.");
        }

        BufferedReader bufReader = new BufferedReader(reader);

        CsvToBean<Student> csvToBean = new CsvToBeanBuilder(bufReader)
                .withType(Student.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<StudentDTO> students = csvToBean.parse()
                .stream()
                .map(s -> this.modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());

        List<String> ids = new ArrayList<>(
                students.stream()
                        .map(s -> this.modelMapper.map(s.getId(), String.class))
                        .collect(Collectors.toList())
        );
        this.addAll(students);
        List<Boolean> enrolled = new ArrayList<Boolean>(this.enrollAll(ids, courseName));

        return enrolled;
    }

    @Override
    public CourseDTO getCourse(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFoundException("Team inesistente");
        }
        return this.modelMapper.map(teamRepository.findById(teamId).get().getCourse(), CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getCourses(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Studentenon trovato.");
        }

        List<CourseDTO> coursesOfStudent = new ArrayList<CourseDTO>(
                studentRepository.getOne(studentId).getCourses()
                        .stream()
                        .map(c -> this.modelMapper.map(c, CourseDTO.class))
                        .collect(Collectors.toList()));
        return coursesOfStudent;
    }


    @Override
    public List<TeamDTO> getTeamsForStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Studente non trovato");
        }

        List<TeamDTO> teams = new ArrayList<TeamDTO>(
                studentRepository.getOne(studentId).getTeams()
                        .stream()
                        .map(t -> this.modelMapper.map(t, TeamDTO.class))
                        .collect(Collectors.toList()));

        return teams;
    }

    @Override
    public List<StudentDTO> getMembers(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException("Team non trovato.");
        }

        List<StudentDTO> members = new ArrayList<>(
                teamRepository.getOne(teamId).getMembers()
                        .stream()
                        .map(s -> this.modelMapper.map(s, StudentDTO.class))
                        .collect(Collectors.toList()));
        return members;
    }

    @Override
    public List<TeamDTO> getTeamForCourse(String courseName) {
        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        List<TeamDTO> teams = new ArrayList<>(
                teamRepository.getTeamByCourse(courseRepository.getOne(courseName))
                        .stream()
                        .map(t -> this.modelMapper.map(t, TeamDTO.class))
                        .collect(Collectors.toList()));
        return teams;
    }

    @Override
    public TeamDTO proposeTeam(String courseName, String name, List<String> memeberIds) {

        // .. Se il corso non esiste
        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }

        Course currCourse = courseRepository.getOne(courseName);

        // .. Se il corso non è abilitato
        if (!currCourse.isEnabled()) {
            throw new CourseNotEnabledException("Corso non abilitato.");
        }


        // .. Rispetto cardinalità del corso
        if (memeberIds.size() < currCourse.getMin() || memeberIds.size() > currCourse.getMax()) {
            throw new TeamException("Numero di membri non adeguato.");
        }

        // .. Controllo duplicati
        for (int i = 0; i < memeberIds.size(); i++) {
            for (int j = i + 1; j < memeberIds.size(); j++) {
                if (memeberIds.get(i).compareTo(memeberIds.get(j)) == 0)
                    throw new TeamException("Non devono esserci membri duplicati.");
            }
        }


        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setCourse(currCourse);


        for (String memeberId : memeberIds) {

            // .. Se lo studente non esiste
            if (!studentRepository.existsById(memeberId)) {
                throw new StudentNotFoundException("Studente non trovato.");
            }

            Student currStudent = studentRepository.getOne(memeberId);

            // .. Se lo studente non è iscritto al corso
            if (!currCourse.contains(currStudent)) {
                throw new TeamException("Lo studente deve essere iscritto al corso.");
            }

            // .. Se lo studente è in un altro gruppo
            for (Team teamOfTheCourse : currCourse.getTeams()) {
                if (teamOfTheCourse.contains(currStudent)) {
                    throw new TeamException("Lo studente non deve appartenere a nessun altro team.");
                }
            }

            newTeam.addMember(currStudent); // Questo metodo chiama anche per lo studente l'addTeam.
        }
        teamRepository.save(newTeam);

        return modelMapper.map(newTeam, TeamDTO.class);
    }


    @Override
    public void setTeamStatus(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFoundException("Team inesistente");
       }
        teamRepository.getOne(teamId).setStatus(1);
    }

    @Override
    public void evictTeam(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFoundException("Team inesistente");
        }


        Team team = teamRepository.getOne(teamId);
        teamRepository.delete(team);
        team.getCourse().removeTeam(team);
        team.getMembers().stream().forEach(
                s -> s.removeTeam(team));
    }

    @Override
    public List<StudentDTO> getStudentsInTeam(String courseName) {
        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato. ");
        }
        return courseRepository.getStudentsInTeams(courseName)
                .stream()
                .map(s -> this.modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getAvailableStudents(String courseName) {
        if (!courseRepository.existsById(courseName)) {
            throw new CourseNotFoundException("Corso non trovato.");
        }
        return courseRepository.getStudentsNotInTeams(courseName)
                .stream()
                .map(s -> this.modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO getTeam(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new TeamNotFoundException("Team non trovato.");
        }
        return this.modelMapper.map(teamRepository.findById(teamId), TeamDTO.class);
    }
}
