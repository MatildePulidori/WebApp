package it.polito.ai.lab3.controllers;

import it.polito.ai.lab3.dtos.CourseDTO;
import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.services.TeamServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/API/teams")
public class TeamController {

    @Autowired
    private TeamServices teamServices;

    @GetMapping({"/{teamId}"})
    public TeamDTO getOne(@PathVariable Long teamId) {
        return ModelHelper.enrich(teamServices.getTeam(teamId));
    }

    @GetMapping({"/{teamId}/course"})
    public CourseDTO getCourse(@PathVariable Long teamId) {
        return ModelHelper.enrich(teamServices.getCourse(teamId));
    }

}
