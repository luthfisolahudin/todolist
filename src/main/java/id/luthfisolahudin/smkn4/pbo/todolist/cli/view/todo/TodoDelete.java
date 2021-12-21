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
        System.out.println("ID: " + data.getId().getValue());
        System.out.println("Nama: " + data.getName().getValue());
        System.out.println("Deskripsi: " + data.getDescription().getValue());
        System.out.println("Status: " + StringUtils.capitalize(data.getStatus().toString()));
        System.out.println("[list] Kembali ke daftar Todo");
        System.out.println("[back] Kembali ke detail Todo");
        System.out.print("Apakah Anda yakin ingin menghapus item ini? (Y/n) ");
        String inputChoice = input.nextLine();

        return Map.ofEntries(
                Map.entry("choice", inputChoice)
        );
    }
}
