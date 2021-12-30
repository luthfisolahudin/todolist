package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoDelete {
    private static final Scanner input = new Scanner(System.in);

    public static Map<String, String> display(Todo data) {
        String name = data.getName().getValue();
        String description = data.hasDescription() ? data.getDescription().getValue() : "-";
        String status = StringUtils.capitalize(data.getStatus().toString().toLowerCase());

        System.out.println("Nama: " + name);
        System.out.println("Deskripsi: " + description);
        System.out.println("Status: " + status);
        System.out.println();
        System.out.println("[list] Kembali ke daftar Todo");
        System.out.println("[back] Kembali ke detail Todo");
        System.out.println();
        System.out.print("Apakah Anda yakin ingin menghapus item ini? (Y/n) ");
        String inputChoice = input.nextLine();
        System.out.println();

        return Map.ofEntries(
                Map.entry("choice", inputChoice)
        );
    }
}
