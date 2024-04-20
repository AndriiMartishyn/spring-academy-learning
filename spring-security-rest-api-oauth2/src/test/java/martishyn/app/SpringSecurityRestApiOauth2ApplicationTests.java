package martishyn.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityRestApiOauth2ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnACashCardWhenDataIsSaved() throws Exception {
        mockMvc.perform(get("/cashcards/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.owner").value("sarah1"));
    }

    @Test
    @DirtiesContext
    void shouldCreateANewCashCard() throws Exception {
        String responseLocation = mockMvc.perform(post("/cashcards")
                        .contentType("application/json")
                        .content("""
                                {
                                    "amount": 250.00,
                                    "owner": "sarah1"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(responseLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(250.00))
                .andExpect(jsonPath("$.owner").value("sarah1"));
    }

    @Test
    void shouldReturnAllCashCardsWhenGetAll() throws Exception {
        mockMvc.perform(get("/cashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3))
                .andExpect(jsonPath("$..owner").value(hasItem("sarah1")))
                .andExpect(jsonPath("$..owner").value(hasItem("esuez5")));

    }

}
