package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InputInvalid {
    public static void display() {
        System.out.println("Input yang Anda masukan tidak sesuai.");
    }

    public static void display(Integer min, Integer max) {
        display();
        System.out.printf("Silakah masukan angka di antara %d dan %d.\n", min, max);
    }

    public static void display(String positive, String negative) {
        display();
        System.out.printf("Silakah masukan antara %s dan %s.\n", positive, negative);
    }
}
