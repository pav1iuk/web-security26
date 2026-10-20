package chnu.edu.websecurity26;

/*
  @author   Pavliuk
  @project  web-security26
  @class    AccessTests
  @version  1.0.0
  @since    20.10.2026 - 21.46
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccessTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeAll() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // --- GET /api/v1/cars (Main endpoint) ---

    @Test
    @WithAnonymousUser
    public void whenAnonymThenStatusUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/cars"));
        var status = result.andReturn().getResponse().getStatus();
        var message = result.andReturn().getResponse().getErrorMessage();
        assertEquals(401, status);
        assertNotNull(message);
        assertEquals("Unauthorized", message);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUserGetsAllCarsThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminGetsAllCarsThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk());
    }

    // --- /hello/admin Endpoint (@PreAuthorize("hasRole('ADMIN')")) ---

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAuthenticatedThenStatusOk() throws Exception {
        var result = mockMvc.perform(get("/api/v1/cars/hello/admin"));
        var status = result.andReturn().getResponse().getStatus();
        var content = result.andReturn().getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("Hello Admin!", content);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUserAccessAdminEndpointThenForbidden() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/v1/cars/hello/admin"));
        var status = resultActions.andReturn().getResponse().getStatus();
        assertEquals(403, status);
        var message = resultActions.andReturn().getResponse().getErrorMessage();
        assertEquals("Forbidden", message);
    }

    @Test
    @WithAnonymousUser
    void whenAnonymAccessAdminEndpointThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/cars/hello/admin"))
                .andExpect(status().isUnauthorized());
    }

    // --- /hello/user Endpoint (@PreAuthorize("hasRole('USER')")) ---

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAuthenticatedThenStatus403() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/v1/cars/hello/user"));
        var status = resultActions.andReturn().getResponse().getStatus();
        assertEquals(403, status);
        var message = resultActions.andReturn().getResponse().getErrorMessage();
        assertEquals("Forbidden", message);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUserAccessUserEndpointThenOk() throws Exception {
        var result = mockMvc.perform(get("/api/v1/cars/hello/user"));
        assertEquals(200, result.andReturn().getResponse().getStatus());
        assertEquals("Hello User!", result.andReturn().getResponse().getContentAsString());
    }

    // --- /hello/man Endpoint (@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")) ---

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminAccessManEndpointThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/cars/hello/man"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "superadmin", roles = {"SUPERADMIN"})
    void whenSuperadminAccessManEndpointThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/cars/hello/man"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUserAccessManEndpointThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/cars/hello/man"))
                .andExpect(status().isForbidden());
    }

    // --- /hello/unknown Endpoint (Public endpoint) ---

    @Test
    @WithAnonymousUser
    void whenAnonymAccessUnknownEndpointThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/cars/hello/unknown"))
                .andExpect(status().isUnauthorized());
    }

    // --- CRUD Operations (POST, PUT, DELETE) ---

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminCreatesCarThenOk() throws Exception {
        String jsonCar = "{\"id\":\"100\",\"brand\":\"BMW\",\"model\":\"M3\",\"year\":2024}";
        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCar))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminUpdatesCarThenOk() throws Exception {
        String jsonCar = "{\"brand\":\"BMW\",\"model\":\"M3 Competition\",\"year\":2025}";
        mockMvc.perform(put("/api/v1/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCar))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminDeletesCarThenOk() throws Exception {
        mockMvc.perform(delete("/api/v1/cars/1"))
                .andExpect(status().isOk());
    }
}