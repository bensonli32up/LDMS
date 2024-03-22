package ldms.technical.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "AmortizationScheduleEntity")
@Table(name = "amortization_schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmortizationSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer period;

    private BigDecimal payment;

    private BigDecimal principal;

    private BigDecimal interest;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleRecord scheduleRecord;
}
