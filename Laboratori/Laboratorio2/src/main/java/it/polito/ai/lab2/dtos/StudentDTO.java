package it.polito.ai.lab2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Data;

@Data
public class StudentDTO {

    @CsvBindByName
    private String id;

    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String name;
}
