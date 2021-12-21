package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoDetail {
    private static final Scanner input = new Scanner(System.in);
    private static final Map<Todo.TodoStatus, String> statusTexts = Map.ofEntries(
            Map.entry(Todo.TodoStatus.PENDING, "Tandai selesai"),
            Map.entry(Todo.TodoStatus.DONE, "Tandai belum selesai")
    );

    public static Map<String, String> display(Todo todo) {
        String name = todo.getName().getValue();
        String description = todo.hasDescription() ? todo.getDescription().getValue() : "-";
        String status = StringUtils.capitalize(todo.getStatus().toString().toLowerCase());

        System.out.println("Nama Todo: " + name);
        System.out.println("Deskripsi Todo: " + description);
        System.out.println("Status Todo: " + status);
        System.out.println();
        System.out.println("Aksi:");
        System.out.println("    1. " + statusTexts.get(todo.getStatus()));
        System.out.println("    2. Ubah Nama");
        System.out.println("    3. Ubah Deskripsi");
        System.out.println("    4. Hapus");
        System.out.println("[list] Kembali ke daftar Todo");
        System.out.println("[quit] Keluar dari aplikasi");
        System.out.println();
        System.out.print("Pilihan Anda: ");
        String inputAction = input.nextLine();
        System.out.println();

        return Map.ofEntries(
                Map.entry("action", inputAction)
        );
    }
}
