package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoUpdateDescription {
    private static final Scanner input = new Scanner(System.in);

    public static Map<String, String> display(Todo todo) {
        System.out.println("[cancel] Membatalkan perubahan");
        System.out.println();
        System.out.print("Masukan Deskripsi Baru Todo: ");
        String inputDescription = input.nextLine();
        System.out.println();

        return Map.ofEntries(
                Map.entry("description", inputDescription)
        );
    }
}
