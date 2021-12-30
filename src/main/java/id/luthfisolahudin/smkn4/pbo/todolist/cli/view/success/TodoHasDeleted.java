package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.success;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoHasDeleted {
    public static void display() {
        System.out.println("Todo telah berhasil dihapus.");
        System.out.println();
    }

    public static void display(Todo.TodoId id) {
        System.out.printf("Todo dengan ID %d berhasil dihapus.", id.getValue());
        System.out.println();
    }
}
