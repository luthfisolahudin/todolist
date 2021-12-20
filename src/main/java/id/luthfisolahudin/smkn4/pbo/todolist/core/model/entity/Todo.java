package id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Description;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Name;
import lombok.*;

@AllArgsConstructor
@Builder
@Data(staticConstructor = "of")
public class Todo {
    @Builder.Default
    final TodoId id = null;

    @NonNull
    Name name;

    @NonNull
    Description description;

    @Value(staticConstructor = "of")
    public static class TodoId {
        @NonNull
        Long value;
    }
}
