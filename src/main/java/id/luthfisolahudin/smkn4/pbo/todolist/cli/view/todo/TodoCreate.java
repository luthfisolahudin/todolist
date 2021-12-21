package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoCreate {
    private static final Scanner input = new Scanner(System.in);

    public static Map<String, String> display() {
        System.out.print("Masukan nama Todo: ");
        String inputName = input.nextLine();
        System.out.print("Masukan deskripsi Todo: ");
        String inputDescription = input.nextLine();
        System.out.println();

        return Map.ofEntries(
                Map.entry("name", inputName),
                Map.entry("description", inputDescription)
        );
    }
}
