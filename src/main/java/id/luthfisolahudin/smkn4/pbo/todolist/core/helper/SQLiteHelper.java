package id.luthfisolahudin.smkn4.pbo.todolist.core.helper;

import id.luthfisolahudin.smkn4.pbo.todolist.core.Configuration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLiteHelper {
    private static final String driverClass = "org.sqlite.JDBC";
    private static final String database = Configuration.databasePath();

    private static SQLiteHelper instance;

    public static SQLiteHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteHelper();
        }

        return instance;
    }

    public Connection getConnection() {
        log.debug("Getting database connection...");
        log.debug("Database: [SQLite] " + database);

        try {
            Class.forName(driverClass);

            return DriverManager.getConnection(database);
        } catch (ClassNotFoundException | SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }
}
