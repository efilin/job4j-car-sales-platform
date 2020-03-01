package ru.job4j.carsalesplatform.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.carsalesplatform.model.SellingCar;
import ru.job4j.carsalesplatform.service.ValidateSellingCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarListController {

    @Autowired
    ValidateSellingCar validateSellingCar;

    @GetMapping("/carslist")
    public String showCars(Model model,
                           @ModelAttribute("filter") String filter,
                           @ModelAttribute("manufacturers") String manufacturers) {
        if (filter.isEmpty()) {
            filter = "allCars";
        }
        List<SellingCar> carList = filterCars(filter, manufacturers);
        model.addAttribute("allCars", carList);
        return "CarsView";
    }

    private List<SellingCar> filterCars(String filter, String manufacturer) {
        List<SellingCar> result = new ArrayList<>();
        if (filter.equals("allCars")) {
            result = validateSellingCar.findAllCars();
        } else if (filter.equals("lastDay")) {
            result = validateSellingCar.findLastDayCars();
        } else if (filter.equals("withPhoto")) {
            result = validateSellingCar.findCarsWithPhoto();
        } else if (filter.equals("currentManufacturer")) {
            result = validateSellingCar.findCurrentManufacturerCars(manufacturer);
        }
        return result;
    }

    @GetMapping("/manufacturer")
    public @ResponseBody
    List<String> getManufacturer() {
        List<SellingCar> carList = validateSellingCar.findAllCars();
        return carList
                .stream()
                .map(SellingCar::getManufacturer)
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/photo",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getPhoto(@RequestParam Integer carId) throws IOException {
        SellingCar sellingCar = validateSellingCar.findCarById(carId);
        String photo = sellingCar.getPhoto();
        InputStream in = new FileInputStream(photo);
        return IOUtils.toByteArray(in);
    }
}
