package ldms.technical.challenge.controller;

import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import ldms.technical.challenge.service.AmortisationScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/amortization-schedules")
@Slf4j
public class AmortizationScheduleController {
    private final AmortisationScheduleService amortisationScheduleService;

    @Autowired
    public AmortizationScheduleController(final AmortisationScheduleService amortisationScheduleService) {
        this.amortisationScheduleService = amortisationScheduleService;
    }

    /**
     * Creates a new amortization schedule based on the provided information.
     *
     * @param newSchedule the details of the new amortization schedule to create.
     * @return A {@link ResponseEntity} containing the created {@link ScheduleRecordVO} and HTTP status.
     */
    @PostMapping("/")
    public ResponseEntity<ScheduleRecordVO> createAmortizationSchedule(@Validated @RequestBody final NewAmortisationScheduleVO newSchedule) {
        log.info("Receive creates a new amortization schedule request");
        final ScheduleRecordVO createdSchedule = amortisationScheduleService.createAmortisationSchedule(newSchedule);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    /**
     * Retrieves all existing schedule record.
     *
     * @return A {@link ResponseEntity} containing a list of {@link ScheduleRecordVO} objects and HTTP status.
     */
    @GetMapping("/")
    public ResponseEntity<List<ScheduleRecordVO>> getAllAmortizationSchedules() {
        log.info("Receive get all schedule record request");
        List<ScheduleRecordVO> schedules = amortisationScheduleService.findAll();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    /**
     * Retrieves a specific schedule record by its ID.
     *
     * @param id The ID of the schedule record to retrieve.
     * @return A {@link ResponseEntity} containing the requested {@link ScheduleRecordVO} and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleRecordVO> getAmortizationScheduleById(@PathVariable Integer id) {
        log.info("Receive get a schedule record request");
        return new ResponseEntity<>(amortisationScheduleService.findById(id), HttpStatus.OK);
    }
}
