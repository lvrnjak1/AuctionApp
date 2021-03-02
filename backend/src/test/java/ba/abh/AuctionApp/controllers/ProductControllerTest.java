package ba.abh.AuctionApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products/1/images")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"imageUrl\": \"nekiurl\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
    }
}