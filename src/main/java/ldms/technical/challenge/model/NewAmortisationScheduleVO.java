package ldms.technical.challenge.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewAmortisationScheduleVO {

    @NotNull(message = "Asset cost must not be null.")
    @PositiveOrZero(message = "Asset cost must be zero or positive.")
    private BigDecimal assetCost;

    @NotNull(message = "Deposit must not be null.")
    @PositiveOrZero(message = "Deposit must be zero or positive.")
    private BigDecimal deposit;

    @NotNull(message = "Interest rate must not be null.")
    @PositiveOrZero(message = "Interest rate must be zero or positive.")
    private BigDecimal interestRate;

    @NotNull(message = "Number of payments must not be null.")
    @PositiveOrZero(message = "Number of payments must be zero or positive.")
    @Max(value = 999, message = "The number of payments must not exceed 999.")
    private Integer numberOfPayments;

    @PositiveOrZero(message = "Balloon payment must be zero or positive if provided.")
    private BigDecimal balloonPayment;
}
