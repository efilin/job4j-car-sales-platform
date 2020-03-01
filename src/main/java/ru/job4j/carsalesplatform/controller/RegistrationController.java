package ru.job4j.carsalesplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.service.ValidateSeller;


@Controller
public class RegistrationController {

    @Autowired
    ValidateSeller validateSeller;

    @GetMapping("/login")
    public ModelAndView loginView(Model model,
                                  @RequestParam(required = false) String error) {
        if (error != null && error.equals("true")) {
            model.addAttribute("error", "Credential invalid");
        }
        return new ModelAndView("LoginView", "seller", new Seller());
    }
}
