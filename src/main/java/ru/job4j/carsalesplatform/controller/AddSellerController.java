package ru.job4j.carsalesplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.service.ValidateSeller;

@Controller
public class AddSellerController {

    @Autowired
    ValidateSeller validateSeller;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/addseller")
    public ModelAndView addSellerView() {
        return new ModelAndView("AddSellerView", "seller", new Seller());
    }

    @PostMapping("/addseller")
    public String addSeller(@ModelAttribute("seller") Seller seller,
                            Model model) {
        seller.setActive(true);
        seller.setRole("USER");
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        validateSeller.addSeller(seller);
        model.addAttribute("userAdd", "User Successfully added");
        return "LoginView";
    }

}
