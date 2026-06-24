package com.awbd.gamerent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EndToEndScenariosTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user123", roles = {"USER"})
    void scenario1_UserCanViewGamesList() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk());
    }

    @Test
    void scenario2_GuestCannotAccessProtectedForm() throws Exception {
        mockMvc.perform(get("/games/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void scenario3_AdminCanAccessProtectedForm() throws Exception {
        mockMvc.perform(get("/games/new"))
                .andExpect(status().isOk());
    }
}