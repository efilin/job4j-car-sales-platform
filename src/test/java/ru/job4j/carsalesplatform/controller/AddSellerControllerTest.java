package ru.job4j.carsalesplatform.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.carsalesplatform.model.Seller;
import ru.job4j.carsalesplatform.service.ValidateSeller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AddSellerController.class)
public class AddSellerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ValidateSeller validateSeller;
    @MockBean
    PasswordEncoder encoder;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenGetAddSellerThenViewPage() throws Exception {
        this.mvc.perform(
                get("/addseller").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("AddSellerView"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenPostAddSellerThenAdd() throws Exception {
        Seller seller = new Seller(0, "qwe", "qwe", "123", "USER", 321, true, null);
        given(this.encoder.encode(seller.getPassword())).willReturn("123");
        this.mvc.perform(post("/addseller")
                .param("name", "qwe")
                .param("username", "qwe")
                .param("password", "123")
                .param("phone", "321"))
                .andExpect(status().isOk())
                .andExpect(view().name("LoginView"));
        verify(this.validateSeller, times(1)).addSeller(seller);
    }
}