package fu.se.lab3_he191060.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fu.se.lab3_he191060.pojos.Employee;
import fu.se.lab3_he191060.services.IEmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/employees returns list of employees")
    void getAllEmployees_returnsList() throws Exception {
        Employee e1 = new Employee("E001", "Alice", "Dev", 1000.0);
        Employee e2 = new Employee("E002", "Bob", "Tester", 900.0);

        given(employeeService.findAll()).willReturn(List.of(e1, e2));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].empId", is("E001")))
                .andExpect(jsonPath("$[1].empId", is("E002")));
    }

    @Test
    @DisplayName("GET /api/employees/{id} returns employee when found")
    void getEmployeeById_found() throws Exception {
        Employee e1 = new Employee("E001", "Alice", "Dev", 1000.0);
        given(employeeService.findById("E001")).willReturn(Optional.of(e1));

        mockMvc.perform(get("/api/employees/{empId}", "E001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId", is("E001")))
                .andExpect(jsonPath("$.name", is("Alice")));
    }

    @Test
    @DisplayName("GET /api/employees/{id} returns 404 when not found")
    void getEmployeeById_notFound() throws Exception {
        given(employeeService.findById("E999")).willReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/{empId}", "E999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/employees creates employee when valid")
    void createEmployee_success() throws Exception {
        Employee request = new Employee("E003", "Charlie", "Manager", 1500.0);
        Employee saved = new Employee("E003", "Charlie", "Manager", 1500.0);

        given(employeeService.save(any(Employee.class))).willReturn(saved);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.empId", is("E003")))
                .andExpect(jsonPath("$.name", is("Charlie")));
    }

    @Test
    @DisplayName("POST /api/employees returns 400 when service throws IllegalArgumentException")
    void createEmployee_badRequestOnIllegalArgument() throws Exception {
        Employee request = new Employee("E004", "Dave", "DevOps", 1200.0);

        given(employeeService.save(any(Employee.class)))
                .willThrow(new IllegalArgumentException("Invalid employee data"));

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Invalid employee data")));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} updates employee when valid")
    void updateEmployee_success() throws Exception {
        Employee request = new Employee("E005", "Eve", "Lead", 2000.0);
        Employee updated = new Employee("E005", "Eve", "Lead", 2100.0);

        given(employeeService.update(eq("E005"), any(Employee.class))).willReturn(updated);

        mockMvc.perform(put("/api/employees/{empId}", "E005")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId", is("E005")))
                .andExpect(jsonPath("$.salary", is(2100.0)));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} returns 400 when service throws IllegalArgumentException")
    void updateEmployee_badRequestOnIllegalArgument() throws Exception {
        Employee request = new Employee("E006", "Frank", "Intern", 500.0);

        given(employeeService.update(eq("E006"), any(Employee.class)))
                .willThrow(new IllegalArgumentException("Invalid update"));

        mockMvc.perform(put("/api/employees/{empId}", "E006")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Invalid update")));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} returns 404 when service throws RuntimeException")
    void updateEmployee_notFoundOnRuntimeException() throws Exception {
        Employee request = new Employee("E007", "Grace", "Analyst", 1100.0);

        given(employeeService.update(eq("E007"), any(Employee.class)))
                .willThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(put("/api/employees/{empId}", "E007")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} returns 204 after deletion")
    void deleteEmployee_noContent() throws Exception {
        doNothing().when(employeeService).deleteById("E008");

        mockMvc.perform(delete("/api/employees/{empId}", "E008"))
                .andExpect(status().isNoContent());
    }
}