package id.luthfisolahudin.smkn4.pbo.todolist.core.controller;

import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.repository.TodoRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoCoreController {
    public static List<Todo> search(String query) {
        return TodoRepository.search(query);
    }

    public static Todo.TodoId create(Todo data) {
        return TodoRepository.insert(data);
    }

    public static List<Todo> get() {
        return TodoRepository.getAll();
    }

    public static Todo get(Todo.TodoId id) {
        return TodoRepository.getById(id);
    }

    public static Todo update(Todo.TodoId id, Todo data) {
        return TodoRepository.update(id, data);
    }

    public static Boolean delete(Todo.TodoId id) {
        return TodoRepository.delete(id);
    }
}
