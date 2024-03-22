package ldms.technical.challenge.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AmortizationScheduleVO {
    private Integer id;

    private Integer period;

    private BigDecimal payment;

    private BigDecimal roundedPayment;

    private BigDecimal principal;

    private BigDecimal roundedPrincipal;

    private BigDecimal interest;

    private BigDecimal roundedInterest;

    private BigDecimal balance;

    private BigDecimal roundedBalance;


}
