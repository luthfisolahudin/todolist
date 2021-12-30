package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoNotDeleted {
    public static void display() {
        System.out.println("Gagal menghapus Todo.");
        System.out.println();
    }

    public static void display(Todo.TodoId id) {
        System.out.printf("Gagal menghapus Todo dengan ID: %d.\n", id.getValue());
        System.out.println();
    }
}
