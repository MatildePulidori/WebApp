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
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Team> teams = new ArrayList<>();

    private int min;
    private int max;
    private boolean enabled;

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", enabled=" + enabled +
                '}';
    }

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

    public Team addTeam(Team team){
        if (team == null){
            return null;
        }
        if (this.contains(team)){
            return null;
        }
        teams.add(team);
        if (team.getCourse()!=this) team.setCourse(this);
        return team;
    }

    public boolean removeTeam(Team team){
        if (team == null){
            return false;
        }
        if (!this.contains(team)){
            return false;
        }
        this.teams.remove(team);
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
