package ldms.technical.challenge.util;

import ldms.technical.challenge.entity.AmortizationSchedule;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static java.math.MathContext.DECIMAL128;

public class CalculateUtil {

    private static final MathContext MC = DECIMAL128;

    public static List<AmortizationSchedule> createAmortisationSchedule(NewAmortisationScheduleVO vo) {

        final List<AmortizationSchedule> scheduleList = new ArrayList<>();
        final BigDecimal P = vo.getAssetCost().subtract(vo.getDeposit());
        final BigDecimal r = vo.getInterestRate().divide(BigDecimal.valueOf(1200), MC);
        final int n = vo.getNumberOfPayments();
        BigDecimal monthlyRepayment;

        if (vo.getBalloonPayment() == null || vo.getBalloonPayment().compareTo(BigDecimal.ZERO) == 0) {
            monthlyRepayment = calculateMonthlyRepayment(P, r, n);
        } else {
            monthlyRepayment = calculateMonthlyRepaymentWithBalloon(P, r, n, vo.getBalloonPayment());
        }

        BigDecimal remainingBalance = P;

        for (int i = 1; i <= n; i++) {
            final BigDecimal interest = remainingBalance.multiply(r, MC);
            final BigDecimal principal = monthlyRepayment.subtract(interest);

            remainingBalance = remainingBalance.subtract(principal);

            final AmortizationSchedule scheduleVO = new AmortizationSchedule();
            scheduleVO.setPeriod(i);
            scheduleVO.setPayment(monthlyRepayment);
            scheduleVO.setPrincipal(principal);
            scheduleVO.setInterest(interest);
            scheduleVO.setBalance(remainingBalance);

            scheduleList.add(scheduleVO);

        }

        return scheduleList;
    }

    public static BigDecimal calculateMonthlyRepayment(BigDecimal P, BigDecimal r, int n) {
        BigDecimal onePlusRPowerN = r.add(BigDecimal.ONE).pow(n);

        return P.multiply(r.multiply(onePlusRPowerN))
                .divide(onePlusRPowerN.subtract(BigDecimal.ONE), MC);
    }

    public static BigDecimal calculateMonthlyRepaymentWithBalloon(BigDecimal P, BigDecimal r, int n, BigDecimal B) {

        return (P.subtract(B.divide((BigDecimal.ONE.add(r)).pow(n), MC))).multiply(r.divide(BigDecimal.ONE.subtract((BigDecimal.ONE.add(r)).pow(-n, MC)), MC));
    }
}
