package it.polito.ai.prj1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws Exception{
        ObjectMapper objectMapper= new ObjectMapper();
        Message msg1 = new Message("Hello, Maven!",
                LocalDateTime.now());
        System.out.println(objectMapper.writeValueAsString(msg1));
        //System.out.println("Hello Maven!");
    }
}
