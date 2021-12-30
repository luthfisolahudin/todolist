package id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoIdNotFound {
    public static void display() {
        System.out.println("ID yang Anda masukan tidak ditemukan.");
        System.out.println();
    }
}
