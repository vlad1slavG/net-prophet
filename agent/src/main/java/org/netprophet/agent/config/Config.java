package org.netprophet.agent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.netprophet.agent.service.DefaultApplicationArguments;
import org.netprophet.agent.interfaces.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "org.netprophet.agent")
public class Config {

    private final Environment environment;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ApplicationArguments applicationArguments() {
        return new DefaultApplicationArguments(environment.getProperty("sun.java.command"));
    }

    @Bean
    public File workingDir() throws URISyntaxException {
        return new File(Paths.get(Config.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()).getParent().toUri());
    }
}
