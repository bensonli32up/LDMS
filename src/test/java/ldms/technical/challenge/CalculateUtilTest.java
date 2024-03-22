package ldms.technical.challenge;

import ldms.technical.challenge.entity.AmortizationSchedule;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.util.CalculateUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static ldms.technical.challenge.util.TestUtil.testingNewAmortisationScheduleWithBalloon;
import static ldms.technical.challenge.util.TestUtil.testingNewAmortisationScheduleWithoutBalloon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CalculateUtilTest {

    @Test
    void testCreateAmortisationScheduleWithoutBalloonPayment() {

        final NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();

        final BigDecimal expectedMonthlyPayment = new BigDecimal("1735.15");
        final BigDecimal[] expectedInterests = {
                new BigDecimal("125.00"), new BigDecimal("114.94"), new BigDecimal("104.81"),
                new BigDecimal("94.62"), new BigDecimal("84.37"), new BigDecimal("74.05"),
                new BigDecimal("63.67"), new BigDecimal("53.22"), new BigDecimal("42.71"),
                new BigDecimal("32.13"), new BigDecimal("21.49"), new BigDecimal("10.78")
        };
        final BigDecimal[] expectedPrincipals = {
                new BigDecimal("1610.15"), new BigDecimal("1620.21"), new BigDecimal("1630.34"),
                new BigDecimal("1640.53"), new BigDecimal("1650.78"), new BigDecimal("1661.10"),
                new BigDecimal("1671.48"), new BigDecimal("1681.93"), new BigDecimal("1692.44"),
                new BigDecimal("1703.02"), new BigDecimal("1713.66"), new BigDecimal("1724.37")
        };
        final BigDecimal[] expectedBalances = {
                new BigDecimal("18389.85"), new BigDecimal("16769.64"), new BigDecimal("15139.30"),
                new BigDecimal("13498.77"), new BigDecimal("11847.99"), new BigDecimal("10186.89"),
                new BigDecimal("8515.41"), new BigDecimal("6833.49"), new BigDecimal("5141.05"),
                new BigDecimal("3438.03"), new BigDecimal("1724.37"), new BigDecimal("0.00")
        };

        final List<AmortizationSchedule> scheduleList = CalculateUtil.createAmortisationSchedule(vo);

        // Verify
        assertNotNull(scheduleList, "The schedule list should not be null.");
        assertEquals(expectedPrincipals.length, scheduleList.size(), "The schedule list size should match the expected number of payments.");

        for (int i = 0; i < scheduleList.size(); i++) {
            final AmortizationSchedule schedule = scheduleList.get(i);
            assertEquals(expectedMonthlyPayment, schedule.getPayment().setScale(2, RoundingMode.HALF_UP), "Monthly payment for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedInterests[i], schedule.getInterest().setScale(2, RoundingMode.HALF_UP), "Interest for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedPrincipals[i], schedule.getPrincipal().setScale(2, RoundingMode.HALF_UP), "Principal for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedBalances[i], schedule.getBalance().setScale(2, RoundingMode.HALF_UP), "Balance for period " + (i + 1) + " does not match expected.");
        }
    }

    @Test
    void testCreateAmortisationScheduleWithBalloonPayment() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithBalloon();

        List<AmortizationSchedule> scheduleList = CalculateUtil.createAmortisationSchedule(vo);

        final BigDecimal expectedMonthlyPayment = new BigDecimal("930.07");
        final BigDecimal[] expectedPrincipals = {
                new BigDecimal("805.07"), new BigDecimal("810.11"), new BigDecimal("815.17"),
                new BigDecimal("820.26"), new BigDecimal("825.39"), new BigDecimal("830.55"),
                new BigDecimal("835.74"), new BigDecimal("840.96"), new BigDecimal("846.22"),
                new BigDecimal("851.51"), new BigDecimal("856.83"), new BigDecimal("862.19")
        };
        final BigDecimal[] expectedInterests = {
                new BigDecimal("125.00"), new BigDecimal("119.97"), new BigDecimal("114.91"),
                new BigDecimal("109.81"), new BigDecimal("104.68"), new BigDecimal("99.52"),
                new BigDecimal("94.33"), new BigDecimal("89.11"), new BigDecimal("83.85"),
                new BigDecimal("78.57"), new BigDecimal("73.24"), new BigDecimal("67.89")
        };
        final BigDecimal[] expectedBalances = {
                new BigDecimal("19194.93"), new BigDecimal("18384.82"), new BigDecimal("17569.65"),
                new BigDecimal("16749.39"), new BigDecimal("15924.00"), new BigDecimal("15093.45"),
                new BigDecimal("14257.71"), new BigDecimal("13416.74"), new BigDecimal("12570.52"),
                new BigDecimal("11719.02"), new BigDecimal("10862.19"), new BigDecimal("10000.00") // Final balance equals the balloon payment
        };

        // Verify
        assertNotNull(scheduleList, "The schedule list should not be null.");
        assertEquals(12, scheduleList.size(), "The schedule list size should match the expected number of payments.");

        for (int i = 0; i < scheduleList.size(); i++) {
            AmortizationSchedule schedule = scheduleList.get(i);
            assertEquals(expectedMonthlyPayment, schedule.getPayment().setScale(2, RoundingMode.HALF_UP), "Payment for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedPrincipals[i], schedule.getPrincipal().setScale(2, RoundingMode.HALF_UP), "Principal for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedInterests[i], schedule.getInterest().setScale(2, RoundingMode.HALF_UP), "Interest for period " + (i + 1) + " does not match expected.");
            assertEquals(expectedBalances[i], schedule.getBalance().setScale(2, RoundingMode.HALF_UP), "Balance for period " + (i + 1) + " does not match expected.");
        }
    }
}

