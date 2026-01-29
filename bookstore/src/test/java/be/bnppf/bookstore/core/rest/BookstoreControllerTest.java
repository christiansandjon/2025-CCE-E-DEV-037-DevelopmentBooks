package be.bnppf.bookstore.core.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookstoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void healthEndpoint_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/bookstore/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bookstore API is running"));
    }

    @Test
    public void getBooksEndpoint_shouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/api/bookstore/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0]").value("CLEAN_CODE"))
                .andExpect(jsonPath("$[1]").value("THE_CLEAN_CODER"))
                .andExpect(jsonPath("$[2]").value("CLEAN_ARCHITECTURE"))
                .andExpect(jsonPath("$[3]").value("TEST_DRIVEN_DEVELOPMENT"))
                .andExpect(jsonPath("$[4]").value("WORKING_EFFECTIVELY_WITH_LEGACY_CODE"));
    }

    @Test
    public void calculatePrice_emptyBasket_shouldReturnZero() throws Exception {
        String requestBody = "[]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(0.0))
                .andExpect(jsonPath("$.message").value("Price calculated successfully"));
    }

    @Test
    public void calculatePrice_oneBook_shouldReturn50() throws Exception {
        String requestBody = "[\"CLEAN_CODE\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(50.0))
                .andExpect(jsonPath("$.message").value("Price calculated successfully"))
                .andExpect(jsonPath("$.bookCounts.CLEAN_CODE").value(1));
    }

    @Test
    public void calculatePrice_twoDifferentBooks_shouldReturn95() throws Exception {
        String requestBody = "[\"CLEAN_CODE\", \"THE_CLEAN_CODER\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(95.0))
                .andExpect(jsonPath("$.message").value("Price calculated successfully"))
                .andExpect(jsonPath("$.bookCounts.CLEAN_CODE").value(1))
                .andExpect(jsonPath("$.bookCounts.THE_CLEAN_CODER").value(1));
    }

    @Test
    public void calculatePrice_threeDifferentBooks_shouldReturn135() throws Exception {
        String requestBody = "[\"CLEAN_CODE\", \"THE_CLEAN_CODER\", \"CLEAN_ARCHITECTURE\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(135.0));
    }

    @Test
    public void calculatePrice_fourDifferentBooks_shouldReturn160() throws Exception {
        String requestBody = "[\"CLEAN_CODE\", \"THE_CLEAN_CODER\", \"CLEAN_ARCHITECTURE\", \"TEST_DRIVEN_DEVELOPMENT\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(160.0));
    }

    @Test
    public void calculatePrice_fiveDifferentBooks_shouldReturn187point5() throws Exception {
        String requestBody = "[\"CLEAN_CODE\", \"THE_CLEAN_CODER\", \"CLEAN_ARCHITECTURE\", \"TEST_DRIVEN_DEVELOPMENT\", \"WORKING_EFFECTIVELY_WITH_LEGACY_CODE\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(187.5));
    }

    @Test
    public void calculatePrice_complexCase_eightBooks_shouldReturn320() throws Exception {
        String requestBody = "[" +
                "\"CLEAN_CODE\", \"CLEAN_CODE\"," +
                "\"THE_CLEAN_CODER\", \"THE_CLEAN_CODER\"," +
                "\"CLEAN_ARCHITECTURE\", \"CLEAN_ARCHITECTURE\"," +
                "\"TEST_DRIVEN_DEVELOPMENT\"," +
                "\"WORKING_EFFECTIVELY_WITH_LEGACY_CODE\"" +
                "]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(320.0))
                .andExpect(jsonPath("$.message").value("Price calculated successfully"))
                .andExpect(jsonPath("$.bookCounts.CLEAN_CODE").value(2))
                .andExpect(jsonPath("$.bookCounts.THE_CLEAN_CODER").value(2))
                .andExpect(jsonPath("$.bookCounts.CLEAN_ARCHITECTURE").value(2))
                .andExpect(jsonPath("$.bookCounts.TEST_DRIVEN_DEVELOPMENT").value(1))
                .andExpect(jsonPath("$.bookCounts.WORKING_EFFECTIVELY_WITH_LEGACY_CODE").value(1));
    }

    @Test
    public void calculatePrice_invalidBookName_shouldReturnBadRequest() throws Exception {
        String requestBody = "[\"INVALID_BOOK\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.price").value(0.0))
                .andExpect(jsonPath("$.message").value("Invalid book name: INVALID_BOOK"))
                .andExpect(jsonPath("$.bookCounts").isEmpty());
    }

    @Test
    public void calculatePrice_mixedValidAndInvalidBooks_shouldReturnBadRequest() throws Exception {
        String requestBody = "[\"CLEAN_CODE\", \"INVALID_BOOK\"]";

        mockMvc.perform(post("/api/bookstore/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid book name: INVALID_BOOK"));
    }
}