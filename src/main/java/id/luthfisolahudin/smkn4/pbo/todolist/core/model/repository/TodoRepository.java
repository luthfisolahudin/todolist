package id.luthfisolahudin.smkn4.pbo.todolist.core.model.repository;

import com.alexfu.sqlitequerybuilder.api.Column;
import com.alexfu.sqlitequerybuilder.api.ColumnConstraint;
import com.alexfu.sqlitequerybuilder.api.ColumnType;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.builder.CreateTableSegmentBuilder;
import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import id.luthfisolahudin.smkn4.pbo.todolist.core.Configuration;
import id.luthfisolahudin.smkn4.pbo.todolist.core.helper.SQLiteHelper;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoRepository {
    private static final String table = Configuration.databaseTableName();
    private static final List<Column> columns = List.of(
            // Column `id`
            new Column(
                    "id",
                    ColumnType.INTEGER,
                    ColumnConstraint.PRIMARY_KEY_AUTO_INCREMENT
            ),

            // Column `name`
            new Column(
                    "name",
                    ColumnType.TEXT,
                    ColumnConstraint.NOT_NULL
            ),

            // Column `description`
            new Column(
                    "description",
                    ColumnType.TEXT
            ),

            // Column `status`
            new Column(
                    "status",
                    ColumnType.TEXT,
                    ColumnConstraint.NOT_NULL,
                    "PENDING"
            )
    );
    private static final SQLiteHelper helper = SQLiteHelper.getInstance();

    public static void initialize() {
        log.debug("Initialize..");
        log.debug("Creating table " + table);

        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryCreateTable().toString();

            statement.execute(query);
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }
    }

    private static SegmentBuilder queryCreateTable() {
        final CreateTableSegmentBuilder queryBuilder = SQLiteQueryBuilder.create()
                .table(table)
                .ifNotExists();

        for (Column column : columns) {
            queryBuilder.column(column);
        }

        return queryBuilder;
    }

    public static Boolean has(Todo.TodoId id) {
        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryCountById(id).toString();

            final ResultSet resultSet = statement.executeQuery(query);
            final int countItemById = resultSet.getInt(1);

            return countItemById >= 1;
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return false;
    }

    private static SegmentBuilder queryCountById(Todo.TodoId id) {
        return SQLiteQueryBuilder.select("count(id)")
                .from(table)
                .where("id = " + id.getValue());
    }

    public static List<Todo> search(String searchQuery) {
        final List<Todo> todoList = new ArrayList<>();

        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = querySearch(searchQuery).toString();

            final ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                todoList.add(Todo.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return todoList;
    }

    private static SegmentBuilder querySearch(String query) {
        final Set<String> searchableFields = Set.of(
                "name",
                "description"
        );
        final String queryWithLikeClosure = "'%" + query + "%'";
        final String whereCondition = searchableFields.stream()
                .map(field -> String.format("'%s' like %s", field, queryWithLikeClosure))
                .collect(Collectors.joining(" or "));

        return SQLiteQueryBuilder.select("*")
                .from(table)
                .where(whereCondition);
    }

    public static Todo getById(Todo.TodoId id) {
        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryGetById(id).toString();

            final ResultSet resultSet = statement.executeQuery(query);

            return Todo.fromResultSet(resultSet);
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }

    private static SegmentBuilder queryGetById(Todo.TodoId id) {
        return SQLiteQueryBuilder.select("*")
                .from(table)
                .where("id = " + id.getValue())
                .limit(1);
    }

    public static List<Todo> getAll() {
        final List<Todo> todoList = new ArrayList<>();

        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryGetAll().toString();

            final ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                todoList.add(Todo.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return todoList;
    }

    private static SegmentBuilder queryGetAll() {
        return SQLiteQueryBuilder.select("*")
                .from(table);
    }

    public static Todo.TodoId insert(Todo todo) {
        try {
            final String query = queryInsert(todo).toString();
            final String[] returnColumn = new String[]{
                    "id",
            };

            @Cleanup final PreparedStatement preparedStatement = helper.getConnection()
                    .prepareStatement(query, returnColumn);

            preparedStatement.execute();

            Long insertedId = preparedStatement.getGeneratedKeys().getLong(1);

            return Todo.TodoId.of(insertedId);
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }

    private static SegmentBuilder queryInsert(Todo todo) {
        final Map<String, String> data = new TreeMap<>() {{
            put("name", todo.getName().getValue());
            put("description", todo.getDescription().getValue());
            put("status", todo.getStatus().toString());
        }};

        return SQLiteQueryBuilder.insert()
                .into(table)
                .columns(data.keySet().toArray(String[]::new))
                .values((Object[]) data.values().toArray(String[]::new));
    }

    public static Todo update(Todo.TodoId id, Todo todo) {
        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryUpdate(id, todo);

            statement.execute(query);

            return getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }

    private static String queryUpdate(Todo.TodoId id, Todo todo) {
        final Map<String, String> data = new HashMap<>() {{
            put("name", todo.getName().getValue());
            put("description", todo.getDescription().getValue());
            put("status", todo.getStatus().toString());
        }};
        final Map<String, String> conditional = Map.ofEntries(
                Map.entry("id", id.getValue().toString())
        );

        String dataQuery = data.entrySet().stream()
                .map(entry -> {
                    String value = entry.getValue() != null ?
                            String.format("'%s'", entry.getValue()) :
                            null;

                    return String.format("%s = %s", entry.getKey(), value);
                })
                .collect(Collectors.joining(", "));
        String conditionalQuery = conditional.entrySet().stream()
                .map(entry -> String.format("%s = '%s'", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        return new StringJoiner(" ")
                .add("update")
                .add(table)
                .add("set")
                .add(dataQuery)
                .add("where")
                .add(conditionalQuery) + ";";
    }

    public static Boolean delete(Todo.TodoId id) {
        try {
            @Cleanup final Statement statement = helper.getConnection().createStatement();
            final String query = queryDelete(id).toString();

            final int affectedRow = statement.executeUpdate(query);

            return affectedRow >= 1;
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return false;
    }

    private static SegmentBuilder queryDelete(Todo.TodoId id) {
        return SQLiteQueryBuilder.delete()
                .from(table)
                .where("id = " + id.getValue());
    }
}
