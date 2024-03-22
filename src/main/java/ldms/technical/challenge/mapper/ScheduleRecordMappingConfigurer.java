package ldms.technical.challenge.mapper;

import ldms.technical.challenge.config.AbstractMappingConfigurer;
import ldms.technical.challenge.entity.ScheduleRecord;
import ldms.technical.challenge.model.AmortizationScheduleVO;
import ldms.technical.challenge.model.NewAmortisationScheduleVO;
import ldms.technical.challenge.model.ScheduleRecordVO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component("ScheduleRecordMappingConfigurer")
public class ScheduleRecordMappingConfigurer extends AbstractMappingConfigurer {

    @Override
    public void configure(final ModelMapper modelMapper) {
        configureEntityVOMappings(modelMapper);
        configureVOEntityMappings(modelMapper);
    }

    private void configureVOEntityMappings(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(NewAmortisationScheduleVO.class, ScheduleRecord.class);
    }

    private void configureEntityVOMappings(final ModelMapper modelMapper) {
        modelMapper.createTypeMap(ScheduleRecord.class, ScheduleRecordVO.class).setPostConverter(toScheduleRecordVOConverter);
    }

    private final Converter<ScheduleRecord, ScheduleRecordVO> toScheduleRecordVOConverter = context -> {

        ScheduleRecord src = context.getSource();
        ScheduleRecordVO dest = context.getDestination();

        dest.setId(src.getId());
        dest.setAssetCost(src.getAssetCost());
        dest.setDeposit(src.getDeposit());
        dest.setInterestRate(src.getInterestRate());
        dest.setNumberOfPayments(src.getNumberOfPayments());
        dest.setBalloonPayment(src.getBalloonPayment());

        if (src.getAmortizationSchedules() != null) {
            List<AmortizationScheduleVO> amortizationScheduleVOList = src.getAmortizationSchedules().stream()
                    .map(schedule -> {
                        AmortizationScheduleVO scheduleVO = new AmortizationScheduleVO();
                        scheduleVO.setId(schedule.getId());
                        scheduleVO.setPeriod(schedule.getPeriod());
                        scheduleVO.setPayment(schedule.getPayment());

                        scheduleVO.setPrincipal(schedule.getPrincipal());

                        scheduleVO.setInterest(schedule.getInterest());
                        scheduleVO.setBalance(schedule.getBalance());

                        // Add rounding logic
                        scheduleVO.setRoundedBalance(schedule.getBalance().setScale(2, RoundingMode.HALF_UP));
                        scheduleVO.setRoundedPayment(schedule.getPayment().setScale(2, RoundingMode.HALF_UP));
                        scheduleVO.setRoundedInterest(schedule.getInterest().setScale(2, RoundingMode.HALF_UP));
                        scheduleVO.setRoundedPrincipal(schedule.getPrincipal().setScale(2, RoundingMode.HALF_UP));

                        return scheduleVO;
                    })
                    .collect(Collectors.toList());

            dest.setAmortizationScheduleVOS(amortizationScheduleVOList);
        }

        return dest;


    };

}
