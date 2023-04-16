package ru.asvronsky.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import ru.asvronsky.bot.configuration.ApplicationConfig;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ConfigurationPropertiesScan
@OpenAPIDefinition(info = @Info(title = "Bot API", version = "1.0.0"))
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }
}
