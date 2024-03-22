package ldms.technical.challenge.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declares the ModelMapper bean.
 */
@Configuration
public class MappingConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();

        /*
         * We don't allow null source properties to be skipped when they are null.
         */
        mapper.getConfiguration()
                // Respect null source properties when mapping
                .setSkipNullEnabled(false)
                // Ensure only source properties that strictly match a destination are mapped
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    }

}
