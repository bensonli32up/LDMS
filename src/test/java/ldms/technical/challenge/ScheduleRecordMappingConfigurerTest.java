package ldms.technical.challenge;

import ldms.technical.challenge.entity.AmortizationSchedule;
import ldms.technical.challenge.entity.ScheduleRecord;
import ldms.technical.challenge.mapper.ScheduleRecordMappingConfigurer;
import ldms.technical.challenge.model.AmortizationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static ldms.technical.challenge.util.TestUtil.testScheduleRecord;
import static ldms.technical.challenge.util.TestUtil.testingAmortizationSchedule;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScheduleRecordMappingConfigurerTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        ScheduleRecordMappingConfigurer configurer = new ScheduleRecordMappingConfigurer();
        configurer.configure(modelMapper);
    }

    @Test
    void testScheduleRecordToScheduleRecordVOConversion() {

        AmortizationSchedule amortizationSchedule = testingAmortizationSchedule();
        ScheduleRecord scheduleRecord = testScheduleRecord();

        ScheduleRecordVO scheduleRecordVO = modelMapper.map(scheduleRecord, ScheduleRecordVO.class);

        assertNotNull(scheduleRecordVO);
        assertEquals(scheduleRecord.getId(), scheduleRecordVO.getId());
        assertEquals(scheduleRecord.getAssetCost(), scheduleRecordVO.getAssetCost());
        assertEquals(scheduleRecord.getDeposit(), scheduleRecordVO.getDeposit());
        assertEquals(scheduleRecord.getNumberOfPayments(), scheduleRecordVO.getNumberOfPayments());
        assertEquals(scheduleRecord.getBalloonPayment(), scheduleRecordVO.getBalloonPayment());
        assertEquals(scheduleRecord.getInterestRate(), scheduleRecordVO.getInterestRate());

        List<AmortizationScheduleVO> amortizationScheduleVOS = scheduleRecordVO.getAmortizationScheduleVOS();
        assertNotNull(amortizationScheduleVOS);
        assertEquals(1, amortizationScheduleVOS.size());

        AmortizationScheduleVO vo = amortizationScheduleVOS.get(0);
        assertEquals(amortizationSchedule.getId(), vo.getId());
        assertEquals(0, vo.getRoundedBalance().compareTo(amortizationSchedule.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP)));
        assertEquals(0, vo.getRoundedPayment().compareTo(amortizationSchedule.getPayment().setScale(2, BigDecimal.ROUND_HALF_UP)));
        assertEquals(0, vo.getRoundedInterest().compareTo(amortizationSchedule.getInterest().setScale(2, BigDecimal.ROUND_HALF_UP)));
        assertEquals(0, vo.getRoundedPrincipal().compareTo(amortizationSchedule.getPrincipal().setScale(2, BigDecimal.ROUND_HALF_UP)));
    }
}