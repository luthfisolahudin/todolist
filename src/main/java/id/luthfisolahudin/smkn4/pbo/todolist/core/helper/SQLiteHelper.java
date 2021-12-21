package id.luthfisolahudin.smkn4.pbo.todolist.core.helper;

import com.alexfu.sqlitequerybuilder.api.Column;
import com.alexfu.sqlitequerybuilder.api.ColumnConstraint;
import com.alexfu.sqlitequerybuilder.api.ColumnType;
import id.luthfisolahudin.smkn4.pbo.todolist.core.Configuration;
import id.luthfisolahudin.smkn4.pbo.todolist.core.common.ColumnConfigurationKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLiteHelper {
    private static final String driverClass = "org.sqlite.JDBC";
    private static final String databasePath = Configuration.databasePath();

    private static SQLiteHelper instance;

    public static SQLiteHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteHelper();
        }

        return instance;
    }

    public Connection getConnection() {
        log.debug("Getting database connection...");
        log.debug("Database: [SQLite] " + databasePath);

        try {
            Class.forName(driverClass);

            return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (ClassNotFoundException | SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }

    public static Column createColumn(Map<ColumnConfigurationKey, Object> columnConfiguration) {
        String name = (String) columnConfiguration.get(ColumnConfigurationKey.NAME);
        ColumnType type = (ColumnType) columnConfiguration.get(ColumnConfigurationKey.TYPE);
        ColumnConstraint constraint = (ColumnConstraint) columnConfiguration.getOrDefault(
                ColumnConfigurationKey.CONSTRAINT,
                null
        );
        String defaultValue = (String) columnConfiguration.getOrDefault(
                ColumnConfigurationKey.DEFAULT_VALUE,
                null
        );

        return new Column(name, type, constraint, defaultValue);
    }
}
