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
import org.testcontainers.junit.jupiter.Testcontainers;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;

@Testcontainers
public abstract class IntegrationEnvironment {
    
    static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("admin")
            .withPassword("admin")
            .withReuse(true);
        POSTGRES.start();
        
        runMigrations();
    }

    private static void runMigrations() {
        Path changelogPath = Paths.get("").toAbsolutePath()
            .getParent()
            .resolve("migrations");
        
        try(Connection conn = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(), 
            POSTGRES.getUsername(), 
            POSTGRES.getPassword()
        )) {
            Database db = new PostgresDatabase();
            db.setConnection(new JdbcConnection(conn)); 

            Liquibase liquibase = new Liquibase(
                "changelog-master.xml", 
                new DirectoryResourceAccessor(changelogPath), 
                db
            );
            liquibase.update(new Contexts(), new LabelExpression());
            liquibase.close();
        } catch (FileNotFoundException | SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
    
    @DynamicPropertySource
    static void registerPostgresProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
