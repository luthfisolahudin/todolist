package id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Description;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Builder
@Data
@Log4j2
public class Todo {
    @Builder.Default
    final TodoId id = null;

    @NonNull
    @With
    Name name;

    @Builder.Default
    @NonNull
    @With
    Description description = Description.of(null);

    @Builder.Default
    @NonNull
    @With
    TodoStatus status = TodoStatus.PENDING;

    public Boolean hasDescription() {
        return !description.isBlank();
    }

    public static Todo fromResultSet(ResultSet resultSet) {
        try {
            final Todo.TodoId id = Todo.TodoId.of(resultSet.getLong("id"));
            final Name name = Name.of(resultSet.getString("name"));
            final Description description = Description.of(resultSet.getString("description"));
            final Todo.TodoStatus status = Todo.TodoStatus.valueOf(resultSet.getString("status"));

            return Todo.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .status(status)
                    .build();
        } catch (SQLException e) {
            log.log(Level.FATAL, e.getStackTrace());
        }

        return null;
    }

    @Value(staticConstructor = "of")
    public static class TodoId {
        @NonNull
        Long value;
    }

    public enum TodoStatus {
        PENDING,
        DONE,
    }
}
