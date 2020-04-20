package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    String name;
    int status;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable( name="team_memebers",
            joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name="student_id"))
    List<Student> members = new ArrayList<>();

    public void setCourse(Course course){
        if (course!=null) {
            this.course = course;
            course.addTeam(this);
        }
    }

    public boolean removeCourse(Course course){
        if(course!=null && this.course == course){
            this.course = null;
            return true;
        }
        return false;
    }

    public Student addMember(Student student){
        if (student==null)
            return null;
        if (this.contains(student))
            return null;
        members.add(student);
        return student;

    }

    public boolean removeMember(Student student){
        if (student==null)
            return false;
        if (!this.contains(student)){
            return false;
        }
        this.members.remove(student);
        return true;
    }

    private boolean contains(Student student) {
        for(Student s: members){
            if (s.getId() == student.getFirstName()){
                return true;
            }
        }
        return false;
    }

}
