package ba.abh.AuctionApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuctionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAuctionSuccess() throws Exception {
        //auctions can't be created in the past
        //that's why dates are so far in the future
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-02-25T19:45:00+01:00\",\n" +
                        "    \"endDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sellerId", is(1)))
                .andExpect(jsonPath("$.startPrice", is(140)))
                .andExpect(jsonPath("$.product.name", is("Barbie")))
                .andExpect(jsonPath("$.product.description", is("Pink barbie doll")))
                .andExpect(jsonPath("$.product.category.name", is("Toys")))
                .andExpect(jsonPath("$.product.images", hasSize(0)))
                .andExpect(jsonPath("$.product.size", is("SMALL")));
    }

    @Test
    public void testCreateAuctionNotSeller() throws Exception {
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("BUYER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-02-25T19:45:00+01:00\",\n" +
                        "    \"endDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateAuctionUnauthorized() throws Exception {
        mockMvc.perform(post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-02-25T19:45:00+01:00\",\n" +
                        "    \"endDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateAuctionNoStartDate() throws Exception {
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"endDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Start date and time must be present")));
    }

    @Test
    public void testCreateAuctionNoEndDate() throws Exception {
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("End date and time must be present")));
    }

    @Test
    public void testCreateAuctionBadDateFormat() throws Exception {
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-03-23 21:45:00+01:00\",\n" +
                        "    \"endDateTime\": \"2022-03-24T21:45:00+01:00\",\n" +
                        "    \"startPrice\": 140,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAuctionNotAllProperties() throws Exception {
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": {}}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", containsString("Start date and time must be present")))
                .andExpect(jsonPath("message", containsString("End date and time must be present")))
                .andExpect(jsonPath("message", containsString("Start price must be present")))
                .andExpect(jsonPath("message", containsString("Name must be present")))
                .andExpect(jsonPath("message", containsString("Category id must be present")))
                .andExpect(jsonPath("message", containsString("Size must be present")))
                .andExpect(jsonPath("message", containsString("Provide at least one color")))
                .andExpect(jsonPath("message", containsString("Start price must be present")));
    }

    @Test
    public void testCreateAuctionWrongPrice() throws Exception {
        //auctions can't be created in the past
        //that's why dates are so far in the future
        mockMvc.perform(post("/auctions")
                .with(user("lamija.vrnjak@gmail.com").password("password").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"startDateTime\": \"2022-02-25T19:45:00+01:00\",\n" +
                        "    \"endDateTime\": \"2022-03-23T21:45:00+01:00\",\n" +
                        "    \"startPrice\": -25,\n" +
                        "    \"product\": {\n" +
                        "        \"name\": \"Barbie\",\n" +
                        "        \"description\": \"Pink barbie doll\",\n" +
                        "        \"categoryId\": 18,\n" +
                        "        \"size\": \"SMALL\",\n" +
                        "        \"colors\": [1,7]\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", containsString("Start price can't be lower than zero")));
    }
}