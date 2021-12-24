package id.luthfisolahudin.smkn4.pbo.todolist;

import id.luthfisolahudin.smkn4.pbo.todolist.cli.controller.TodoController;
import id.luthfisolahudin.smkn4.pbo.todolist.core.controller.TodoCoreController;

public class Main {
    public static void main(String[] args) {
        TodoCoreController.initialize();
        TodoController.index();
    }
}
