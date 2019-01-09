package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.listener.OnRegistrationCompleteEvent;
import pl.sda.javapoz.model.entity.UserEntity;
import pl.sda.javapoz.service.UserService;
import pl.sda.javapoz.validator.RegisterValidator;

import javax.validation.Valid;


@Controller
public class UserRegisterController {

    private UserService userService;
    private RegisterValidator registerValidator;
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserRegisterController(UserService userService, RegisterValidator registerValidator, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.registerValidator = registerValidator;
        this.eventPublisher = eventPublisher;
    }


    @GetMapping("/register")
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addUser(@ModelAttribute @Valid UserEntity user,
                                BindingResult bindResult, ModelAndView modelAndView) {
        if (bindResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else if (registerValidator.isEmailTaken(user)) {
            modelAndView.addObject("emailIsTaken", "Użytkownik o podanym emailu juz istnieje!");
            modelAndView.addObject("emailIsTakenInfo", "Wprowadź inny mail lub zaloguj się na istniejące konto!");

            return registrationPage(modelAndView);
        } else {
            userService.saveUser(user);
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }


}
