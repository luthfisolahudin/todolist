package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoList {
    private static final Scanner input = new Scanner(System.in);

    public static Map<String, String> display(List<Todo> todoList) {
        System.out.println("Daftar Todo:");

        for (Todo todo : todoList) {
            int defaultPadding = 2;

            String id = String.valueOf(todo.getId().getValue());
            String name = todo.getName().getValue();
            String description = todo.hasDescription() ? todo.getDescription().getValue() : "-";
            String status = StringUtils.capitalize(todo.getStatus().toString().toLowerCase());

            String paddingLeft = StringUtils.repeat(" ", id.length() + defaultPadding);

            System.out.printf("%s. %s\n", id, name);
            System.out.printf(paddingLeft + "Deskripsi: %s\n", description);
            System.out.printf(paddingLeft + "Status: %s\n", status);
            System.out.println();
        }

        System.out.println("[create] Buat Todo baru");
        System.out.println("  [quit] Keluar dari aplikasi");
        System.out.print("Masukan ID todo yang ingin Anda lihat: ");
        String inputId = input.nextLine();
        System.out.println();

        return Map.ofEntries(
                Map.entry("action", inputId)
        );
    }
}
