package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name="course_id")
    Course course;


}
