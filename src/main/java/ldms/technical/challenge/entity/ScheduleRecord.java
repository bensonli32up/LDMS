package ldms.technical.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "ScheduleRecordEntity")
@Table(name = "schedule_record")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal assetCost;

    private BigDecimal deposit;

    private BigDecimal interestRate;

    private Integer numberOfPayments;

    private BigDecimal balloonPayment;

    @OneToMany(mappedBy = "scheduleRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AmortizationSchedule> amortizationSchedules;
}
