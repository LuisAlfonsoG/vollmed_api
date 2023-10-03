package med.voll.api.controller;

import med.voll.api.domain.consultations.ConsultationDataFields;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationService;
import med.voll.api.domain.doctors.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ConsultationDataRegister> consultationDataRegisterJacksonTester;

    @Autowired
    private JacksonTester<ConsultationDataFields> consultationDataFieldsJacksonTester;

    @MockBean
    private ConsultationService consultationService;

    @Test
    @DisplayName("Returns Status code 400 when data is invalid")
    @WithMockUser
    void addConsultationCase1() throws Exception {

        var response = mvc.perform(post("/consultations")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Returns Status code 400 when data is invalid")
    @WithMockUser
    void addConsultationCase2() throws Exception {

        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        var data = new ConsultationDataFields(1l, "Luis", "Luis", date);

        when(consultationService.validateConsultationData(any())).thenReturn(data);

        var response = mvc.perform(post("/consultations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(consultationDataRegisterJacksonTester.write(new ConsultationDataRegister(2l,5l, date, null)).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = consultationDataFieldsJacksonTester.write(data).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}