package ru.job4j.carsalesplatform.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carsalesplatform.service.ValidateSeller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ValidateSeller validateSeller;

    @Test
    public void whenGetLoginThenViewPage() throws Exception {
        this.mvc.perform(
                get("/login").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("LoginView"));
    }
}