package it.polito.ai.lab2.dtos;

import lombok.Data;

@Data
public class CourseDTO {

    String name;
    int min;
    int max;
    boolean enabled;

}
