package ru.asvronsky.scrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class IntegrationDummyTest extends IntegrationEnvironment {
    
    @Test
    public void testTableExistence() throws SQLException {
        Connection conn = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(), 
            POSTGRES.getUsername(), 
            POSTGRES.getPassword());
        
        DatabaseMetaData md = conn.getMetaData();
        
        ResultSet rs = md.getTables(null, null, "chat_link", new String[] {"TABLE"});
        assertTrue(rs.next());
    }


}
