package ldms.technical.challenge.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ScheduleRecordVO {
    private Integer id;

    private BigDecimal assetCost;

    private BigDecimal deposit;

    private BigDecimal interestRate;

    private Integer numberOfPayments;

    private BigDecimal balloonPayment;

    private List<AmortizationScheduleVO> amortizationScheduleVOS;
}
