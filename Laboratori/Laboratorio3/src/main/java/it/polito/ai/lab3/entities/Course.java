package it.polito.ai.lab3.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
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

    public Student addStudent(Student student) {
        this.students.add(student);
        return student;
    }

    public boolean contains(Student student) {
        for (Student s : students) {
            if (s.getId() == student.getId()) {
                return true;
            }
        }
        return false;
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
        if (team == null) {
            return false;
        }
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
