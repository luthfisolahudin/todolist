package id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Description;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Name;
import lombok.*;
import lombok.extern.log4j.Log4j2;

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
