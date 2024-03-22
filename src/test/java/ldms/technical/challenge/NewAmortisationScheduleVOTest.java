package ldms.technical.challenge;

import jakarta.validation.ConstraintViolation;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.Set;

import static ldms.technical.challenge.util.TestUtil.testingNewAmortisationScheduleWithoutBalloon;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class NewAmortisationScheduleVOTest {
    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        NewAmortisationScheduleVO validVO = testingNewAmortisationScheduleWithoutBalloon();

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(validVO);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenAssetCostIsNegative() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();
        vo.setAssetCost(BigDecimal.valueOf(-1) );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Asset cost must be zero or positive.");
    }

    @Test
    void shouldFailValidationWhenNumberOfPaymentsIsNegative() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();
        vo.setNumberOfPayments(-1 );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number of payments must be zero or positive.");
    }

    @Test
    void shouldFailValidationWhenInterestRateIsNegative() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();
        vo.setInterestRate(BigDecimal.valueOf(-1) );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Interest rate must be zero or positive.");
    }

    @Test
    void shouldFailValidationWhenDepositIsNegative() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();
        vo.setDeposit(BigDecimal.valueOf(-1) );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Deposit must be zero or positive.");
    }

    @Test
    void shouldFailValidationWhenBalloonPaymentIsNegative() {
        NewAmortisationScheduleVO vo = testingNewAmortisationScheduleWithoutBalloon();
        vo.setBalloonPayment(BigDecimal.valueOf(-1) );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Balloon payment must be zero or positive if provided.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1000}) // Values outside the valid range
    void shouldFailValidationWhenNumberOfPaymentsIsInvalid(Integer numberOfPayments) {
        NewAmortisationScheduleVO vo = new NewAmortisationScheduleVO(
                new BigDecimal("20000"),
                new BigDecimal("5000"),
                new BigDecimal("5"),
                numberOfPayments,
                new BigDecimal("10000")
        );

        Set<ConstraintViolation<NewAmortisationScheduleVO>> violations = validator.validate(vo);
        assertThat(violations).isNotEmpty();
    }
}
