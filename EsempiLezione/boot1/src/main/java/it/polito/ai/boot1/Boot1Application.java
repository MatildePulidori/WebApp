package it.polito.ai.boot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Boot1Application {

    @Bean
    public Date last() {
        return new Date();
    }

    @Bean
    public List<String> items(Date last) {
        return Stream.of("alpha", "beta", "gamma", last.toString())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        SpringApplication.run(Boot1Application.class, args);

        // 30 marzo 2020:
        /* ApplicationContext ctx = SpringApplication.run(Boot1Application.class, args);
        String[] beanNeams = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNeams);

        for (String name: beanNeams) System.out.println(name);*/
    }

}
