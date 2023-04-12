package ru.asvronsky.scrapper;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;

@Testcontainers
public abstract class IntegrationEnvironment {
    
    @Container
    static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper");
        POSTGRES.start();
        try {
            runMigrations();
        }
        catch (Exception e) {
            throw new RuntimeException("Running migrations failure", e);
        }
    }

    static private void runMigrations() 
            throws SQLException, LiquibaseException, FileNotFoundException {

        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(), 
            POSTGRES.getUsername(), 
            POSTGRES.getPassword()
        );
        Database database = DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(new JdbcConnection(connection)); 

        Path changelog = Paths.get("").toAbsolutePath()
            .getParent()
            .resolve("migrations");
        Liquibase liquibase = new Liquibase(
            "changelog-master.xml", 
            new DirectoryResourceAccessor(changelog), 
            database
        );
        liquibase.update(new Contexts(), new LabelExpression());
        liquibase.close();
    }
    
    @DynamicPropertySource
    static void registerPostgresProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
