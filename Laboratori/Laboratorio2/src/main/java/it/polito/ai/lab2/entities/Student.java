package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {

    @Id
    private String id;
    private String name;
    private String firstName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "studenti_id"),
            inverseJoinColumns = @JoinColumn(name = "course_name"))
    private List<Course> courses = new ArrayList<Course>();

    @ManyToMany(mappedBy = "members")
    private List<Team> teams = new ArrayList<>();

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public Course addCourse(Course course) {
        this.courses.add(course);
        course.addStudent(this);
        return course;
    }

    public Team addTeam(Team team) {
        if (team == null) {
            return null;
        }
        if (this.contains(team)) {
            return null;
        }
        this.teams.add(team);
        return team;
    }

    public boolean removeTeam(Team team) {
        if (team == null)
            return false;
        if (!this.contains(team)) {
            return false;
        }
        this.teams.remove(team);
        return true;
    }

    public boolean contains(Team team) {
        for (Team t : teams) {
            if (t.getId() == team.getId()) {
                return true;
            }
        }
        return false;
    }


}
