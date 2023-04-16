package ru.asvronsky.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import ru.asvronsky.scrapper.configuration.ApplicationConfig;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Swagger API", version = "1.0.0"))
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }
}
