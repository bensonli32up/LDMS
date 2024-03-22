package ldms.technical.challenge.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import jakarta.annotation.PostConstruct;

/**
 * An abstract class that serves as a base for specific ModelMapper configuration.
 */
public abstract class AbstractMappingConfigurer implements MappingConfigurer {
    @Autowired
    @Lazy
    private ModelMapper mapper;

    /**
     * When Spring context is started add any further configuration to the ModelMapper.
     */
    @PostConstruct
    void configureMapper() {
        configure(mapper);
    }
}
