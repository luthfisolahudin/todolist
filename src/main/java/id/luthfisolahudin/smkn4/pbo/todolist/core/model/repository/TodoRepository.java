package id.luthfisolahudin.smkn4.pbo.todolist.core.model.repository;

import com.alexfu.sqlitequerybuilder.api.Column;
import com.alexfu.sqlitequerybuilder.api.ColumnConstraint;
import com.alexfu.sqlitequerybuilder.api.ColumnType;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.builder.CreateTableSegmentBuilder;
import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import id.luthfisolahudin.smkn4.pbo.todolist.core.Configuration;
import id.luthfisolahudin.smkn4.pbo.todolist.core.common.ColumnConfigurationKey;
import id.luthfisolahudin.smkn4.pbo.todolist.core.helper.SQLiteHelper;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Description;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Name;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoRepository {
    private static final String table = Configuration.databaseTableName();
    private static final List<Map<ColumnConfigurationKey, Object>> columns = List.of(
            // Column `id`
            Map.ofEntries(
                    Map.entry(ColumnConfigurationKey.NAME, "id"),
                    Map.entry(ColumnConfigurationKey.TYPE, ColumnType.INTEGER),
                    Map.entry(ColumnConfigurationKey.CONSTRAINT, ColumnConstraint.PRIMARY_KEY_AUTO_INCREMENT)
            ),

            // Column `name`
            Map.ofEntries(
                    Map.entry(ColumnConfigurationKey.NAME, "name"),
                    Map.entry(ColumnConfigurationKey.TYPE, ColumnType.TEXT),
                    Map.entry(ColumnConfigurationKey.CONSTRAINT, ColumnConstraint.NOT_NULL)
            ),

            // Column `description`
            Map.ofEntries(
                    Map.entry(ColumnConfigurationKey.NAME, "description"),
                    Map.entry(ColumnConfigurationKey.TYPE, ColumnType.TEXT)
            )
    );

    private static final SQLiteHelper helper = SQLiteHelper.getInstance();

    public static void initialize() {
        log.debug("Initialize..");
        log.debug("Creating table " + table);

        try {
            Statement statement = helper.getConnection().createStatement();
            String query = queryCreateTable().toString();

            statement.execute(query);
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }
    }

    private static SegmentBuilder queryCreateTable() {
        CreateTableSegmentBuilder queryBuilder = SQLiteQueryBuilder.create()
                .table(table)
                .ifNotExists();

        for (Map<ColumnConfigurationKey, Object> column : columns) {
            queryBuilder.column(createColumn(column));
        }

        return queryBuilder;
    }

    private static Column createColumn(Map<ColumnConfigurationKey, Object> columnConfiguration) {
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

    public static Todo get(Todo.TodoId id) {
        return Todo.builder()
                .id(id)
                .name(Name.of("name"))
                .description(Description.of("description"))
                .build();
    }

    public static List<Todo> search(String query) {
        return Collections.emptyList();
    }
}
