package pt.isel.ls.connection;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDataBase {
    private final String url;

    public ConnectionDataBase(String envVariable) {
        this.url = System.getenv(envVariable);
    }

    public Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(url);
        return ds.getConnection();
    }
}