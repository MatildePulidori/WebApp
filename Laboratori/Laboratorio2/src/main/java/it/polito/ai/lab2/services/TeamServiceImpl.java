package it.polito.ai.lab2.services;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.entities.Course;
import it.polito.ai.lab2.entities.Student;
import it.polito.ai.lab2.repositories.CourseRepository;
import it.polito.ai.lab2.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public List<CourseDTO> getCourses() {
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
        List<StudentDTO> enrolled = new ArrayList<>();
        try {
            if (courseRepository.existsById(courseName)){
                List<Student> students = courseRepository.getOne(courseName).getStudents();
                for (Student s : students) {
                    enrolled.add(modelMapper.map(s, StudentDTO.class));
                }
            }
        } catch (CourseNotFoundException ce) {
            throw new CourseNotFoundException();
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
        Student s = studentRepository.getOne(studentId);
        if (!c.isEnabled()) return false;

        c.addStudent(s);
        return true;
    }

    @Override
    public void enableCourse(String courseName) {
        try{
            this.getCourse(courseName)
                    .ifPresent(c->modelMapper.map(c, Course.class).setEnabled(true));
        } catch (CourseNotFoundException ce){
            throw new CourseNotFoundException();
        }

    }

    @Override
    public void disableCourse(String courseName) {
        try{
            this.getCourse(courseName)
                    .ifPresent(c->modelMapper.map(c, Course.class).setEnabled(false));
        } catch (CourseNotFoundException ce){
            throw new CourseNotFoundException();
        }

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
        List<Boolean> enrolled = new ArrayList<>();
        if (!courseRepository.existsById(courseName)) {
            for(String id : studentIds) {

                boolean added = false;
                if (studentRepository.existsById(id)){
                    added = this.addStudentToCourse(id, courseName);
                }
                enrolled.add(added);



            }
        }
        return enrolled;
    }

    @Override
    public List<Boolean> addAndEnroll(Reader r, String courseName) throws FileNotFoundException {
        r = new FileReader("../mydata/data.csv");



        return null;
    }
}
