package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.repositories.ProductRepository;
import ba.abh.AuctionApp.repositories.auction.AuctionRepository;
import ba.abh.AuctionApp.repositories.category.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    public void testSortByTimeLeft() throws Exception {
        MvcResult result = mockMvc.perform(get("/auctions?sort=TIME_LEFT"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        List<Long> timeLeft = new ArrayList<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        for(int i = 0; i < data.length(); i++) {
            String dateTimeField = String.valueOf(data.getJSONObject(i).get("endDateTime"));
            LocalDateTime endDateTime = LocalDateTime.parse(dateTimeField, inputFormatter);
            timeLeft.add(Duration.between(LocalDateTime.now(), endDateTime).getSeconds());
        }

        List<Long> sortedTimeLeft = new ArrayList<>(timeLeft);
        sortedTimeLeft.sort(null);
        assertEquals(sortedTimeLeft, timeLeft);
    }

    @Test
    public void testSortByTimeLeftDesc() throws Exception {
        MvcResult result = mockMvc.perform(get("/auctions?sort=TIME_LEFT&sortOrder=DESC"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        List<Long> timeLeft = new ArrayList<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        for (int i = 0; i < data.length(); i++) {
            String dateTimeField = String.valueOf(data.getJSONObject(i).get("endDateTime"));
            LocalDateTime endDateTime = LocalDateTime.parse(dateTimeField, inputFormatter);
            timeLeft.add(Duration.between(LocalDateTime.now(), endDateTime).getSeconds());
        }

        List<Long> sortedTimeLeft = new ArrayList<>(timeLeft);
        sortedTimeLeft.sort(null);
        Collections.reverse(sortedTimeLeft);
        assertEquals(sortedTimeLeft, timeLeft);
    }

    @Test
    public void testSeachAuctions1() throws Exception {
        mockMvc.perform(get("/auctions?name=black"))
                .andExpect(jsonPath("$.pagination.pageSize", is(1)))
                .andExpect(jsonPath("$.data[0].product.name", is("Black sandals")));
    }

    @Test
    public void testSearchAuctions2() throws Exception {
        mockMvc.perform(get("/auctions?name=ShirT"))
                .andExpect(jsonPath("$.pagination.pageSize", is(1)));
    }

    @Test
    public void testSearchAuctions3() throws Exception {
        mockMvc.perform(get("/auctions?name=notfound"))
                .andExpect(jsonPath("$.pagination.pageSize", is(0)));
    }

    @Test
    public void testFilterAuctions2() throws Exception {
        MvcResult result = mockMvc.perform(get("/auctions?priceMin=100")).andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            String price = String.valueOf(data.getJSONObject(i).get("startPrice"));
            assertTrue( Double.parseDouble(price) >= 100);
        }
    }

    @Test
    public void testFilterAuctions3() throws Exception {
        MvcResult result = mockMvc.perform(get("/auctions?priceMax=100")).andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            String price = String.valueOf(data.getJSONObject(i).get("startPrice"));
            assertTrue( Double.parseDouble(price) <= 100);
        }
    }

    @Test
    public void testFilterByMultipleCategories() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/auctions?categoryId=8&categoryId=9"))
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            String catId = String.valueOf(data.getJSONObject(i).getJSONObject("product").get("categoryId"));
            assertTrue(catId.equals("8") || catId.equals("9"));
        }
    }

    @Test
    public void testFilterByParentCategory() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/auctions?categoryId=1"))
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            Long catId = (Long) data.getJSONObject(i).getJSONObject("product").get("categoryId");
            Category cat = categoryRepository.findById(catId).orElseThrow();
            assertEquals((long) cat.getParentCategory().getId(), 1L);
        }
    }

    @Test
    public void testMultipleFlters() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/auctions?categoryId=1&priceMax=150"))
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            Long catId = (Long) data.getJSONObject(i).getJSONObject("product").get("categoryId");
            Category cat = categoryRepository.findById(catId).orElseThrow();
            String price = String.valueOf(data.getJSONObject(i).get("startPrice"));
            assertEquals((long) cat.getParentCategory().getId(), 1L);
            assertTrue(Double.parseDouble(price) <= 150);
        }
    }
}