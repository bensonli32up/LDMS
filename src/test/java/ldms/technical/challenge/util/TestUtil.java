package ldms.technical.challenge.util;

import ldms.technical.challenge.entity.AmortizationSchedule;
import ldms.technical.challenge.entity.ScheduleRecord;
import ldms.technical.challenge.model.AmortizationScheduleVO;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class TestUtil {
    public static NewAmortisationScheduleVO testingNewAmortisationScheduleWithoutBalloon() {
        return NewAmortisationScheduleVO.builder()
                .assetCost(new BigDecimal("25000"))
                .deposit(new BigDecimal("5000"))
                .interestRate(new BigDecimal("7.5"))
                .numberOfPayments(12)
                .balloonPayment(null) // No balloon payment
                .build();
    }

    public static NewAmortisationScheduleVO testingNewAmortisationScheduleWithBalloon() {
        return NewAmortisationScheduleVO.builder()
                .assetCost(new BigDecimal("25000"))
                .deposit(new BigDecimal("5000"))
                .interestRate(new BigDecimal("7.5"))
                .numberOfPayments(12)
                .balloonPayment(new BigDecimal("10000")) // No balloon payment
                .build();
    }

    public static ScheduleRecordVO testingScheduleRecordVO() {
        return ScheduleRecordVO.builder()
                .id(1)
                .assetCost(new BigDecimal("20000"))
                .deposit(new BigDecimal("5000"))
                .interestRate(new BigDecimal("5"))
                .numberOfPayments(60)
                .balloonPayment(new BigDecimal("10000"))
                .amortizationScheduleVOS(List.of(testingAmortizationScheduleVO()))
                .build();
    }

    public static AmortizationScheduleVO testingAmortizationScheduleVO() {
        return AmortizationScheduleVO.builder()
                .id(1)
                .period(1)
                .payment(new BigDecimal("1000"))
                .roundedPayment(new BigDecimal("1000"))
                .principal(new BigDecimal("800"))
                .roundedPrincipal(new BigDecimal("800"))
                .interest(new BigDecimal("200"))
                .roundedInterest(new BigDecimal("200"))
                .balance(new BigDecimal("20000"))
                .roundedBalance(new BigDecimal("20000"))
                .build();
    }

    public static AmortizationSchedule testingAmortizationSchedule() {
        return AmortizationSchedule.builder()
                .id(1)
                .period(1)
                .payment(new BigDecimal("1000"))
                .principal(new BigDecimal("800"))
                .interest(new BigDecimal("200"))
                .balance(new BigDecimal("20000"))
                .build();
    }

    public static ScheduleRecord testScheduleRecord() {
        return ScheduleRecord.builder()
                .id(1)
                .assetCost(new BigDecimal("20000"))
                .deposit(new BigDecimal("5000"))
                .interestRate(new BigDecimal("5"))
                .numberOfPayments(12)
                .balloonPayment(new BigDecimal("10000"))
                .amortizationSchedules(Collections.singletonList( testingAmortizationSchedule()))
                .build();
    }


}
