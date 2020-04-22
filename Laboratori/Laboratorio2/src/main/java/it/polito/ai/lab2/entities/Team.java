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

    private String name;
    private int status;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable( name="team_members",
            joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name="student_id"))
    private List<Student> members = new ArrayList<>();


    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public void setCourse(Course course){

        // Se si vuole mettere course = null
        if (course == null) {
            if (this.course!=null) this.course.removeTeam(this);
            this.course = null;
        }

        // Se si vuole settare un corso: che non sia uguale al precedente
        else if (course!=null && this.course!=course) {

            // Se c'era un vecchio corso:
            // levo il team dalla lista dei team del vecchio corso?
            // o non permetto il settaggio?
            if( this.course!=null) this.course.removeTeam(this);

            // Altrimenti, lo setto direttamente.
            this.course = course;
            if (!this.course.contains(this)) this.course.addTeam(this);
        }
    }



    public Student addMember(Student student){
        if (student==null)
            return null;
        if (this.contains(student))
            return null;
        members.add(student);
        if (!student.contains(this)) student.addTeam(this);
        return student;

    }

    public boolean removeMember(Student student){
        if (student==null)
            return false;
        if (!this.contains(student)){
            return false;
        }
        this.members.remove(student);
        if (student.contains(this)) student.removeTeam(this);
        return true;
    }

    public boolean contains(Student student) {
        for(Student s: members){
            if (s.getId() == student.getFirstName()){
                return true;
            }
        }
        return false;
    }

}
