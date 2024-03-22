package ldms.technical.challenge.service;

import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;

import java.util.List;

public interface AmortisationScheduleService {


    /**
     * Retrieves all existing schedule record.
     *
     * @return A list of {@link ScheduleRecordVO} objects.
     */
    List<ScheduleRecordVO> findAll();

    /**
     * Retrieves a specific schedule record by its ID.
     *
     * @param id The ID of the schedule record to retrieve.
     * @return A {@link ScheduleRecordVO} .
     */
    ScheduleRecordVO findById(Integer id);

    /**
     * Creates a new amortization schedule based on the provided information.
     *
     * @param newSchedule the details of the new amortization schedule to create.
     * @return A {@link ScheduleRecordVO}.
     */
    ScheduleRecordVO createAmortisationSchedule(NewAmortisationScheduleVO newSchedule);
}
