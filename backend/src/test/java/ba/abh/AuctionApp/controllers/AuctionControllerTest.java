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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        "    \"startDateTime\":1646305200000,\n" +
                        "    \"endDateTime\": 1648980000000,\n" +
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
                .andExpect(jsonPath("$.startPrice", is(140.0)))
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
                        "    \"startDateTime\": 1646305200000,\n" +
                        "    \"endDateTime\": 1648980000000,\n" +
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
                        "    \"startDateTime\": 1646305200000,\n" +
                        "    \"endDateTime\": 1648980000000,\n" +
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
                        "    \"endDateTime\": 1646305200000,\n" +
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
                        "    \"startDateTime\": 1646305200000,\n" +
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
                        "    \"startDateTime\":1646305200000,\n" +
                        "    \"endDateTime\": 1648980000000,\n" +
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

    @Test
    public void testGetAuctions1() throws Exception {
        mockMvc.perform(get("/auctions"))
                .andExpect(jsonPath("$.pagination.hasNext", is(false)))
                .andExpect(jsonPath("$.pagination.hasPrevious", is(false)))
                .andExpect(jsonPath("$.data", hasSize(8)));
    }

    @Test
    public void testGetAuctions2() throws Exception {
        mockMvc.perform(get("/auctions?limit=3"))
                .andExpect(jsonPath("$.pagination.hasNext", is(true)))
                .andExpect(jsonPath("$.pagination.hasPrevious", is(false)))
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test
    public void testGetAuctions3() throws Exception {
        mockMvc.perform(get("/auctions?page=1&limit=3"))
                .andExpect(jsonPath("$.pagination.hasNext", is(true)))
                .andExpect(jsonPath("$.pagination.hasPrevious", is(false)))
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test
    public void testFindByCategory1() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=1"))
                .andExpect(jsonPath("$.pagination.pageSize", is(7)));
    }

    @Test
    public void testFindByCategory2() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=2"))
                .andExpect(jsonPath("$.pagination.pageSize", is(1)));
    }

    @Test
    public void testFindByCategory3() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=3"))
                .andExpect(jsonPath("$.pagination.pageSize", is(0)));
    }

    @Test
    public void testFindByCategory4() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=9"))
                .andExpect(jsonPath("$.pagination.pageSize", is(4)));
    }

    @Test
    public void testFindByCategory5() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=12"))
                .andExpect(jsonPath("$.pagination.pageSize", is(2)));
    }

    @Test
    public void testFindByCategory6() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=13"))
                .andExpect(jsonPath("$.pagination.pageSize", is(1)));
    }

    @Test
    public void testFindByCategory8() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=8"))
                .andExpect(jsonPath("$.pagination.pageSize", is(1)));
    }

    @Test
    public void testFindByCategory9() throws Exception {
        mockMvc.perform(get("/auctions?categoryId=17"))
                .andExpect(jsonPath("$.pagination.pageSize", is(0)));
    }

    @Test
    public void testFindByIdError() throws Exception {
        mockMvc.perform(get("/auctions/77"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Auction with id 77 doesn't exist")));
    }

    @Test
    public void testFindByIdSuccess1() throws Exception {
        mockMvc.perform(get("/auctions/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(7)))
                .andExpect(jsonPath("startPrice", is(25.0)))
                .andExpect(jsonPath("sellerId", is(5)))
                .andExpect(jsonPath("$.product.id", is(7)))
                .andExpect(jsonPath("$.product.name", is("Wool sweaters")));
    }

    @Test
    public void testFindByIdSuccess2() throws Exception {
        mockMvc.perform(get("/auctions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("startPrice", is(150.0)))
                .andExpect(jsonPath("sellerId", is(1)))
                .andExpect(jsonPath("$.product.id", is(1)))
                .andExpect(jsonPath("$.product.name", is("Black sandals")));
    }
}