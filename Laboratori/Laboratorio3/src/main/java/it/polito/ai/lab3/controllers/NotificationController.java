package it.polito.ai.lab3.controllers;


import it.polito.ai.lab3.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/confirm/{token}")
    String confirm(@PathVariable  String token){
        notificationService.confirm(token);
        return "confirmTeam";

    }

    @GetMapping("/reject/{token}")
    String reject(@PathVariable String token){
        notificationService.reject(token);
        return "rejectTeam";
    }


}
