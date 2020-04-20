package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Student {
    @Id
    String id;
    String name;
    String firstName;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="student_course",
            joinColumns = @JoinColumn(name="studenti_id"),
            inverseJoinColumns = @JoinColumn(name="course_name"))
    List<Course> courses = new ArrayList<Course>();

    public Course addCourse(Course course) {
        if (!this.contains(course)){
            courses.add(course);
            return course;
        }
        return null;
    }

    public boolean contains(Course course){
        for (Course c: courses) {
            if (c.getName() == course.getName()){
                return true;
            }
        }
        return false;
    }
}
