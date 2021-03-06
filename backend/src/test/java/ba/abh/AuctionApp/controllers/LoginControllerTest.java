package ba.abh.AuctionApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"lamija.vrnjak@gmail.com\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginNoUserWithMail() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"lami.vrnjak@gmail.com\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message",
                        is("Email lami.vrnjak@gmail.com isn't associated with an account")));
    }

    @Test
    public void testLoginWrongPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"lamija.vrnjak@gmail.com\",\n" +
                        "    \"password\": \"password123\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Invalid credentials")));
    }

    @Test
    public void testLoginNoEmail() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"password\": \"password123\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Email must be present and have a valid format")));
    }

    @Test
    public void testLoginNoPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"lamija.vrnjak@gmail.com\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Password must be present")));
    }
}
