package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.success;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoHasDeleted {
    public static void display() {
        System.out.println("Todo telah berhasil dihapus.");
    }

    public static void display(Todo.TodoId id) {
        display();
        System.out.println("ID Todo: " + id.getValue());
    }
}
