package ru.job4j.carsalesplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.model.SellingCar;
import ru.job4j.carsalesplatform.service.ValidateSellingCar;

@Controller
public class UpdateCarController {

    @Autowired
    ValidateSellingCar validateSellingCar;

    @PostMapping("/update")
    public String updateCars(@ModelAttribute("carId") Integer carId,
                             Authentication authentication,
                             Model model) {
        SellingCar car = validateSellingCar.findCarById(carId);
        Seller seller = car.getSeller();

        String username = authentication.getName();
        if (seller.getUsername().equals(username)) {
            validateSellingCar.changeSaleStatus(carId);
            model.addAttribute("carUpdate", "Car status is changed");
        } else {
            model.addAttribute("error", "Not enough rights");
        }
        model.addAttribute("allCars", validateSellingCar.findAllCars());
        return "CarsView";
    }

}
