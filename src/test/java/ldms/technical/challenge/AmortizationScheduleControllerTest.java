package ldms.technical.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import ldms.technical.challenge.controller.AmortizationScheduleController;
import ldms.technical.challenge.exception.RecordNotFoundException;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import ldms.technical.challenge.service.AmortisationScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static ldms.technical.challenge.util.TestUtil.testingNewAmortisationScheduleWithoutBalloon;
import static ldms.technical.challenge.util.TestUtil.testingScheduleRecordVO;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AmortizationScheduleController.class)
class AmortizationScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmortisationScheduleService amortisationScheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateAmortizationSchedule() throws Exception {
        final NewAmortisationScheduleVO newSchedule = testingNewAmortisationScheduleWithoutBalloon();

        final ScheduleRecordVO createdSchedule = testingScheduleRecordVO();

        given(amortisationScheduleService.createAmortisationSchedule(newSchedule)).willReturn(createdSchedule);

        mockMvc.perform(post("/service/amortization-schedules/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSchedule)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdSchedule)));
    }

    @Test
    void testGetAllAmortizationSchedules() throws Exception {
        final ScheduleRecordVO schedule = testingScheduleRecordVO();
        final List<ScheduleRecordVO> schedules = Collections.singletonList(schedule);
        given(amortisationScheduleService.findAll()).willReturn(schedules);

        mockMvc.perform(get("/service/amortization-schedules/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(schedules)));
    }

    @Test
    void testGetAmortizationScheduleById() throws Exception {
        final int testId = 1;
        final ScheduleRecordVO schedule = testingScheduleRecordVO();
        given(amortisationScheduleService.findById(testId)).willReturn(schedule);

        mockMvc.perform(get("/service/amortization-schedules/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(schedule)));
    }

    @Test
    void testGetAmortizationScheduleById_NotFound() throws Exception {
        final int testId = 999; // Assuming this ID does not exist
        given(amortisationScheduleService.findById(testId))
                .willThrow(new RecordNotFoundException(testId));

        mockMvc.perform(get("/service/amortization-schedules/{id}", testId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Record: " + testId + " not found.")));
    }

    @Test
    void testCreateAmortizationSchedule_BadInput() throws Exception {
        NewAmortisationScheduleVO newScheduleWithMissingAssetCost = new NewAmortisationScheduleVO();
        // Setting up the object with required fields missing or bad data

        mockMvc.perform(post("/service/amortization-schedules/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newScheduleWithMissingAssetCost)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("status\":\"BAD_REQUEST\"")));
    }
}