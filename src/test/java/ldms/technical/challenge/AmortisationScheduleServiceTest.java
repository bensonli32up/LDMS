package ldms.technical.challenge;

import ldms.technical.challenge.entity.ScheduleRecord;
import ldms.technical.challenge.exception.RecordNotFoundException;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import ldms.technical.challenge.repository.ScheduleRecordRepository;
import ldms.technical.challenge.service.AmortisationScheduleService;
import ldms.technical.challenge.service.AmortisationScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ldms.technical.challenge.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AmortisationScheduleServiceTest {

    @Mock
    private ScheduleRecordRepository scheduleRecordRepository;

    @Mock
    private ModelMapper mapper;

    private AmortisationScheduleService service;

    private ScheduleRecord scheduleRecord;
    private ScheduleRecordVO scheduleRecordVO;

    @BeforeEach
    void setUp() {
        scheduleRecord = testScheduleRecord();
        scheduleRecordVO = testingScheduleRecordVO();
        service = new AmortisationScheduleServiceImpl(scheduleRecordRepository, mapper);
    }

    @Test
    void findAll() {
        when(scheduleRecordRepository.findAll()).thenReturn(Collections.singletonList(scheduleRecord));
        when(mapper.map(any(ScheduleRecord.class), eq(ScheduleRecordVO.class))).thenReturn(scheduleRecordVO);

        List<ScheduleRecordVO> result = service.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(scheduleRecordRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        int id = 1;
        when(scheduleRecordRepository.findById(id)).thenReturn(Optional.of(scheduleRecord));
        when(mapper.map(any(ScheduleRecord.class), eq(ScheduleRecordVO.class))).thenReturn(scheduleRecordVO);

        ScheduleRecordVO result = service.findById(id);

        assertNotNull(result);
        verify(scheduleRecordRepository, times(1)).findById(id);
    }

    @Test
    void findById_RecordNotFound_ThrowException() {
        int id = 1;
        when(scheduleRecordRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void createAmortisationSchedule() {
        NewAmortisationScheduleVO newSchedule = testingNewAmortisationScheduleWithBalloon();
        when(mapper.map(any(NewAmortisationScheduleVO.class), eq(ScheduleRecord.class))).thenReturn(scheduleRecord);
        when(scheduleRecordRepository.saveAndFlush(any(ScheduleRecord.class))).thenReturn(scheduleRecord);
        when(mapper.map(any(ScheduleRecord.class), eq(ScheduleRecordVO.class))).thenReturn(scheduleRecordVO);

        ScheduleRecordVO result = service.createAmortisationSchedule(newSchedule);

        assertNotNull(result);
        verify(scheduleRecordRepository, times(1)).saveAndFlush(any(ScheduleRecord.class));
    }
}