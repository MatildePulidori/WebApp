package it.polito.ai.docker1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

@RestController
public class HomeController {

    @GetMapping("/")
    public String getDate() {
        try {
            InputStream is = Runtime.getRuntime()
                    .exec("uname")
                    .getInputStream();
            Scanner scanner = new Scanner(is);
            return new Date().toString() + ": " + scanner.nextLine();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
