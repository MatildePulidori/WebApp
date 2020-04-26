package it.polito.ai.security1;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MyData {

    String name;
    Date date;

}
