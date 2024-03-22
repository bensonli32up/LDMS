package ldms.technical.challenge.service;

import ldms.technical.challenge.entity.AmortizationSchedule;
import ldms.technical.challenge.entity.ScheduleRecord;
import ldms.technical.challenge.exception.RecordNotFoundException;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import ldms.technical.challenge.repository.ScheduleRecordRepository;
import ldms.technical.challenge.util.CalculateUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AmortisationScheduleServiceImpl implements AmortisationScheduleService {

    private final ScheduleRecordRepository scheduleRecordRepository;

    private final ModelMapper mapper;

    @Autowired
    public AmortisationScheduleServiceImpl(
            final ScheduleRecordRepository scheduleRecordRepository,
            final ModelMapper mapper) {
        this.scheduleRecordRepository = scheduleRecordRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRecordVO> findAll() {
        final List<ScheduleRecord> records = scheduleRecordRepository.findAll();

        return records.stream()
                .map(record -> mapper.map(record, ScheduleRecordVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleRecordVO findById(Integer id) {
        final ScheduleRecord result = scheduleRecordRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
        return mapper.map(result, ScheduleRecordVO.class);
    }

    @Override
    @Transactional
    public ScheduleRecordVO createAmortisationSchedule(final NewAmortisationScheduleVO newSchedule) {
        final ScheduleRecord scheduleRecord = mapper.map(newSchedule, ScheduleRecord.class);

        final List<AmortizationSchedule> resultList = CalculateUtil.createAmortisationSchedule(newSchedule);
        resultList.forEach(result -> result.setScheduleRecord(scheduleRecord));
        scheduleRecord.setAmortizationSchedules(resultList);

        final ScheduleRecord saveedScheduleRecord = scheduleRecordRepository.saveAndFlush(scheduleRecord);

        return mapper.map(saveedScheduleRecord, ScheduleRecordVO.class);
    }
}
