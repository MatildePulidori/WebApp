package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    String name;
    int min;
    int max;
    boolean enabled;
    @ManyToMany(mappedBy = "courses")
    List<Student> students = new ArrayList<Student>();

    public Student addStudent(Student student){
        if (!this.contains(student)){
            students.add(student);
            if (!student.contains(this)) student.addCourse(this);
            return student;
        }
        return null;
    }

    public boolean contains(Student student){
        for (Student s: students) {
            if (s.getId()== student.getId()){
                return true;
            }
        }
        return false;
    }
}
