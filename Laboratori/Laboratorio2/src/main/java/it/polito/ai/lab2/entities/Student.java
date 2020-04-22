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
    @JoinTable(name="student_course",
            joinColumns = @JoinColumn(name="studenti_id"),
            inverseJoinColumns = @JoinColumn(name="course_name"))
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
        if (!this.contains(course)){
            courses.add(course);
            if (!course.contains(this)) course.addStudent(this);
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

    public Team addTeam(Team team){
        if (team == null){
            return null;
        }
        if (this.contains(team)){
            return null;
        }

        teams.add(team);
        return team;
    }

    public boolean removeTeam(Team team){
        if (team==null)
            return false;
        if (!this.contains(team)){
            return false;
        }
        this.teams.remove(team);
        if (team.contains(this)) team.removeMember(this);
        return true;
    }

    public boolean contains(Team team){
        for (Team t: teams) {
            if (t.getId() == team.getId()){
                return true;
            }
        }
        return false;
    }



}
