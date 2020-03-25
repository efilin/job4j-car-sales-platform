package ru.job4j.carsalesplatform.controller;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.model.SellingCar;
import ru.job4j.carsalesplatform.service.ValidateSellingCar;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(CarListController.class)
public class CarListControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ValidateSellingCar validateSellingCar;
    @MockBean
    DataSource dataSource;


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenGetCarsThenShowPage() throws Exception {
        Seller seller = new Seller();
        SellingCar car = new SellingCar(1, "v", "v", 10000, 10000, 1999, "sedan", "1.6", "automatic", "desc", "photo", seller, true, new Timestamp(System.currentTimeMillis()));
        given(this.validateSellingCar.findAllCars()).willReturn(
                new ArrayList<>(Lists.newArrayList(car))
        );
        this.mvc.perform(
                get("/carslist").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("CarsView"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenGetManufacturerAndGetAnswer() throws Exception {
        Seller seller = new Seller();
        SellingCar car1 = new SellingCar(1, "VW", "v", 10000, 10000, 1999, "sedan", "1.6", "automatic", "desc", "photo", seller, true, new Timestamp(System.currentTimeMillis()));
        SellingCar car2 = new SellingCar(1, "MB", "v", 10000, 10000, 1999, "sedan", "1.6", "automatic", "desc", "photo", seller, true, new Timestamp(System.currentTimeMillis()));
        given(this.validateSellingCar.findAllCars()).willReturn(
                new ArrayList<>(Lists.newArrayList(car1, car2))
        );
        this.mvc.perform(
                get("/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"VW\",\"MB\"]"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenUpdateCarThenViewPage() throws Exception {
        Integer carId = 1;
        Seller seller = new Seller(0, "qwe", "qwe", "123", "USER", 321, true, null);
        SellingCar car = new SellingCar(1, "v", "v", 10000, 10000, 1999, "sedan", "1.6", "automatic", "desc", "photo", seller, true, new Timestamp(System.currentTimeMillis()));
        given(this.validateSellingCar.findCarById(carId)).willReturn(car);
        this.mvc.perform(
                post("/update").flashAttr("carId", carId))
                .andExpect(status().isOk())
                .andExpect(view().name("CarsView"));
    }

}