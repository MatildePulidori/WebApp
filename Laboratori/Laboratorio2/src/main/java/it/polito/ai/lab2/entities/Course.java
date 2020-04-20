package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    String name;

    @ManyToMany(mappedBy = "courses")
    List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<Team> teams = new ArrayList<>();

    int min;
    int max;
    boolean enabled;


    public Student addStudent(Student student){
        if (!this.contains(student)){
            students.add(student);
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
