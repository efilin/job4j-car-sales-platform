package ru.job4j.carsalesplatform.controller;

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
import ru.job4j.carsalesplatform.service.ValidateSeller;
import ru.job4j.carsalesplatform.service.ValidateSellingCar;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AddCarController.class)
public class AddCarControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ValidateSellingCar validateSellingCar;

    @MockBean
    private ValidateSeller validateSeller;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenGetAddCarThenPageAddCar() throws Exception {
        this.mvc.perform(
                get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("AddCarView"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenPostAddCarThenAdd() throws Exception {
        Seller seller = new Seller(0, "qwe", "qwe", "123", "USER", 321, true, null);
        SellingCar car = new SellingCar(1, "v", "v", 10000, 10000, 1999, "sedan", "1.6", "automatic", "desc", "photo", seller, true, null);
        this.mvc.perform(
                post("/add")
                        .flashAttr("car", car)
        )
                .andExpect(status().is3xxRedirection());
        verify(this.validateSellingCar, times(1)).addCar(car);
    }

}