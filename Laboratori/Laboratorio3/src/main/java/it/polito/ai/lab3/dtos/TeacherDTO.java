package it.polito.ai.lab3.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TeacherDTO extends RepresentationModel<TeacherDTO> {

    private String id;
}
