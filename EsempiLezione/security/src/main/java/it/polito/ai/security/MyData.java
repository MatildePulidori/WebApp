package it.polito.ai.security;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MyData {

    String name;
    Date date;

}
