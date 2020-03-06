package org.zerock.persistence;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;


@Log4j
public class JDBCTests {
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection(){

        try(Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "book_ex",
                "book_ex")){
            log.info(con);
            log.info("------");
        } catch (Exception e){
            e.getMessage();
        }
    }


}
