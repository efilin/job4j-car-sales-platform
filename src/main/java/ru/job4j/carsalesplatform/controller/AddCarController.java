package ru.job4j.carsalesplatform.controller;


import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.model.SellingCar;
import ru.job4j.carsalesplatform.service.ValidateSeller;
import ru.job4j.carsalesplatform.service.ValidateSellingCar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

@Controller
public class AddCarController {

    @Autowired
    ValidateSellingCar validateSellingCar;
    @Autowired
    ValidateSeller validateSeller;

    @GetMapping("/add")
    public ModelAndView addCarView() {
        return new ModelAndView("AddCarView", "car", new SellingCar());
    }

    @PostMapping("/add")
    public String addCar(@Nullable @ModelAttribute("photoFile") MultipartFile photoFile,
                         @ModelAttribute("car") SellingCar car,
                         Authentication authentication) throws IOException {

        if (/*photoFile != null && */!photoFile.isEmpty()) {
            String uploadString = System.getProperty("java.io.tmpdir") + File.separator + "photo";
            String extension = FilenameUtils.getExtension(photoFile.getOriginalFilename());
            String fileName = String.format("photo-%s.%s", String.valueOf(System.currentTimeMillis()), extension);
            Path path = Paths.get(uploadString, fileName);
            if (Files.notExists(Paths.get(uploadString))) {
                Files.createDirectories(Paths.get(uploadString));
            }
            Files.write(path, photoFile.getBytes());
            car.setPhoto(path.toString());
        }


        car.setOnSale(true);
        car.setCreated(new Timestamp(System.currentTimeMillis()));
        Seller seller = validateSeller.findSellerByLogin(authentication.getName());
        car.setSeller(seller);

        validateSellingCar.addCar(car);
        validateSeller.updateSeller(seller);

        return "redirect:/carslist";
    }

}
