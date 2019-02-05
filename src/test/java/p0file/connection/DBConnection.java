package p0file.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mysql://159.69.118.199:3306/wantsome_java" );
        config.setUsername( "username" );
        config.setPassword( "password" );
        config.setMaximumPoolSize(2);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    protected DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
