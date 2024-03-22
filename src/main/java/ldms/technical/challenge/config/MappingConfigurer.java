package ldms.technical.challenge.config;

import org.modelmapper.ModelMapper;
/**
 * Represents a contract for configuring a ModelMapper instance.
 * */
public interface MappingConfigurer {
    void configure(ModelMapper modelMapper);
}
