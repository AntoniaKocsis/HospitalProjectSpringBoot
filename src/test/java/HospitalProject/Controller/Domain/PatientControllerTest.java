package HospitalProject.Controller.Domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import HospitalProject.Controller.Domain.Patient.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHTML() throws Exception {
        this.mockMvc.perform(get("/patients")).andDo(print()).andExpect(status().isOk());
    }
    @Test
    void addNewPatient() throws Exception {

        String dateString = "2001-12-31T23:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Patient patient = new Patient();
        patient.setId(60);
        patient.setBirthDate(localDateTime);
        patient.setFirstName("Alex");
        patient.setLastName("Stevenson");
        patient.setAddress("Nasaud 16");
        patient.setContact("0752134322");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(patient);
        String content = "id=&firstName=Kocsis&lastName=Antonia&birthDate=2023-12-08T17%3A55&contact=0752134322&address=Strada+Nasaud+n";


        this.mockMvc.perform(post("/patients/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(content))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"))
                .andExpect(flash().attribute("message", "The patient has been saved successfully."));
    }
}