package ba.abh.AuctionApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milić\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.name", is("Milo")))
                .andExpect(jsonPath("$.user.surname", is("Milić")))
                .andExpect(jsonPath("$.user.email", is("milo.milic@etf.unsa.ba")))
                .andExpect(jsonPath("$.user.roles", hasSize(1)))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    public void testRegisterNoName() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"surname\": \"Milić\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Name shouldn't be blank")));
    }

    @Test
    public void testRegisterBlankName() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"\",\n" +
                        "    \"surname\": \"Milić\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Name shouldn't be blank")));
    }

    @Test
    public void testRegisterNoSurname() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Surname shouldn't be blank")));
    }

    @Test
    public void testRegisterBlankSurname() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Surname shouldn't be blank")));
    }

    @Test
    public void testRegisterNoEmail() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milic\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Email shouldn't be blank")));
    }

    @Test
    public void testRegisterBlankEmail() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milic\",\n" +
                        "    \"email\": \"\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Email shouldn't be blank")));
    }

    @Test
    public void testRegisterInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milic\",\n" +
                        "    \"email\": \"milo.milic\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Provide a valid email address")));
    }

    @Test
    public void testRegisterNoPassword() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milic\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Password must be present")));
    }

    @Test
    public void testRegisterInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Milo\",\n" +
                        "    \"surname\": \"Milic\",\n" +
                        "    \"email\": \"milo.milic@etf.unsa.ba\",\n" +
                        "    \"password\": \"pas\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message", is("Password should contain at least 5 characters")));
    }
}
