package it.polito.ai.lab1;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;


@Controller
@Log(topic = "HomeController")
public class HomeController {

    @Autowired
    private RegistrationManager manager;


    @GetMapping({"/", "/home"})
    public String home(Model m) {
        if (!m.containsAttribute("registrazione")) m.addAttribute("registrazione", "");
        return "home";
    }


    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("command") RegistrationCommand command,
                                   Model m) {
        log.info(command.toString());
        return "registration";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("command") RegistrationCommand command,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model m) {

        log.info(command.toString());


        if (!command.validatePassword()) {
            log.warning("Password inserite diverse.");
            bindingResult.addError(new FieldError("command", "passwordValidation", "Password diverse: riscrivi la password."));
        }
        if (!command.isPrivacyCheck()) {
            log.warning("Manca il consenso alla privacy.");
            bindingResult.addError(new FieldError("command", "privacyCheck", "Spunta il campo consenso alla privacy."));
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        RegistrationDetails details = RegistrationDetails.builder()
                .nome(command.nome)
                .cognome(command.cognome)
                .email(command.email)
                .password(command.password)
                .registrationDate(new Date())
                .build();

        if (manager.putIfAbsent(command.email, details) != null) {
            log.warning("Utente " + manager.get(command.email).getEmail() + " già presente.");
            m.addAttribute("registrazione", "Utente " + manager.get(command.email).getEmail() + " già presente.");
            return "registration";
        }
        log.info("Registrazione di " + manager.get(command.email).getEmail() + " andata a buon fine.");
        redirectAttributes.addFlashAttribute("registrazione", "Registrazione di " + manager.get(command.email).getEmail() + " andata a buon fine (" + manager.get(command.email).getRegistrationDate() + ")");
        log.info("redirect:home");
        return "redirect:home";

    }


    @GetMapping("/login")
    public String loginPage(Model m) {

        if (!m.containsAttribute("utenteSbagliato")) m.addAttribute("utenteSbagliato", "Campo email vuoto.");
        if (!m.containsAttribute("passwordSbagliata")) m.addAttribute("passwordSbagliata", "Campo password vuoto.");
        return "login";
    }


    @PostMapping("/login")
    public String login(LoginCommand loginCommand,
                        HttpSession session,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model m) {

        log.info(loginCommand.toString());
        m.addAttribute("utenteSbagliato", "");
        m.addAttribute("passwordSbagliata", "");
        if (loginCommand == null || loginCommand.getEmail() == "" || loginCommand.getPassword() == "") {
            log.info("Riempire campi vuoti.");
            if (loginCommand.getEmail().equals("")) {
                m.addAttribute("utenteSbagliato", "Riempire i campi vuoti.");
            } else if (loginCommand.getPassword().equals("")) {
                m.addAttribute("passwordSbagliata", "Riempire campo password.");
            }

            return "login";
        } else {
            RegistrationDetails details = manager.get(loginCommand.getEmail());
            if (details == null) {
                log.info("Utente inesistente.");
                m.addAttribute("utenteSbagliato", "Campo utente errato.");
                return "login";
            } else {
                boolean pwdCorrect = loginCommand.getPassword().equals(details.getPassword());
                if (pwdCorrect == false) {
                    log.info("Password sbagliata");
                    m.addAttribute("passwordSbagliata", "Password errata.");
                    return "login";
                } else {
                    if (session.getAttribute("username") == null) {
                        session.setAttribute("username", loginCommand);
                        return "redirect:private";
                    }
                }
            }
        }
        return "redirect:home";
    }

    @GetMapping("/private")
    public String privatePage(HttpSession session, Model m) {
        if (session.getAttribute("username") == null) {
            return "redirect:home";
        }
        log.info("Login avvenuto con successo: accesso a sezione privata.");
        return "private";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, Model m) {
        if (session.getAttribute("username") != null) {
            session.removeAttribute("username");
        }
        log.info("Logout avvenuto con successo: ritorno a sezione pubblica.");
        return "redict:home";

    }
}