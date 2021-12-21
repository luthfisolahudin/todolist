package id.luthfisolahudin.smkn4.pbo.todolist.cli.controller;

import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error.InputInvalid;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error.TodoIdNotFound;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.error.TodoNotDeleted;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.success.TodoHasCreated;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.success.TodoHasDeleted;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoCreate;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoDelete;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoDetail;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoList;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoUpdateDescription;
import id.luthfisolahudin.smkn4.pbo.todolist.cli.view.todo.TodoUpdateName;
import id.luthfisolahudin.smkn4.pbo.todolist.core.controller.TodoCoreController;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Description;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.Name;
import id.luthfisolahudin.smkn4.pbo.todolist.core.model.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TodoController {
    public static void index() {
        final Map<String, Runnable> actions = new HashMap<>() {{
            put("create", TodoController::create);
            put("add", this.get("create"));
            put("quit", () -> System.exit(0));
            put("exit", this.get("quit"));
            put("end", this.get("quit"));
        }};
        boolean next = false;

        final List<Todo> todoList = TodoCoreController.get();

        do {
            final Map<String, String> response = TodoList.display(todoList);

            final String inputAction = response.get("action");

            final boolean isInActions = actions.containsKey(inputAction.toLowerCase());

            // Validate
            if (
                    !isInActions &&
                            !StringUtils.isNumeric(inputAction)
            ) {
                InputInvalid.display();
                continue;
            }

            if (isInActions) {
                actions.get(inputAction.toLowerCase()).run();
                next = true;
                break;
            }

            final Todo.TodoId id = Todo.TodoId.of(Long.valueOf(inputAction));
            final Boolean isIdExist = TodoCoreController.isExist(id);

            if (!isIdExist) {
                TodoIdNotFound.display();
                continue;
            }

            detail(id);

            next = true;
        } while (!next);
    }

    public static void create() {
        boolean next = false;

        do {
            final Map<String, String> response = TodoCreate.display();

            final String inputName = response.get("name");
            final String inputDescription = response.get("description");

            final Todo.TodoId createdId = TodoCoreController.create(
                    Todo.builder()
                            .name(Name.of(inputName))
                            .description(Description.of(inputDescription))
                            .build()
            );

            TodoHasCreated.display(createdId);
            TodoController.index();

            next = true;
        } while (!next);
    }

    public static void detail(Todo.TodoId id) {
        final Map<String, Runnable> actions = new HashMap<>() {{
            put("1", () -> TodoController.changeStatus(id));
            put("2", () -> TodoController.updateName(id));
            put("3", () -> TodoController.updateDescription(id));
            put("4", () -> TodoController.delete(id));
            put("index", TodoController::index);
            put("list", this.get("index"));
            put("back", this.get("index"));
            put("cancel", this.get("index"));
            put("quit", () -> System.exit(0));
            put("exit", this.get("quit"));
            put("end", this.get("quit"));
        }};
        boolean next = false;

        final Todo todo = TodoCoreController.get(id);

        do {
            final Map<String, String> response = TodoDetail.display(todo);

            final String action = response.get("action");

            if (!actions.containsKey(action.toLowerCase())) {
                InputInvalid.display();
                continue;
            }

            actions.get(action.toLowerCase()).run();

            next = true;
        } while (!next);
    }

    public static void updateName(Todo.TodoId id) {
        final Map<String, Runnable> actions = new HashMap<>() {{
            put("index", TodoController::index);
            put("list", this.get("index"));
            put("detail", () -> TodoController.detail(id));
            put("back", this.get("detail"));
            put("cancel", this.get("detail"));
        }};
        boolean next = false;

        final Todo todo = TodoCoreController.get(id);

        do {
            final Map<String, String> response = TodoUpdateName.display(todo);

            final String inputName = response.get("name");

            if (actions.containsKey(inputName.toLowerCase())) {
                actions.get(inputName.toLowerCase()).run();
                next = true;
                break;
            }

            TodoCoreController.update(id, todo.withName(Name.of(inputName)));
            TodoController.detail(id);

            next = true;
        } while (!next);
    }

    public static void updateDescription(Todo.TodoId id) {
        final Map<String, Runnable> actions = new HashMap<>() {{
            put("index", TodoController::index);
            put("list", this.get("index"));
            put("detail", () -> TodoController.detail(id));
            put("back", this.get("detail"));
            put("cancel", this.get("detail"));
        }};
        boolean next = false;

        final Todo todo = TodoCoreController.get(id);

        do {
            final Map<String, String> response = TodoUpdateDescription.display(todo);

            final String inputDescription = response.get("description");

            if (actions.containsKey(inputDescription.toLowerCase())) {
                actions.get(inputDescription.toLowerCase()).run();
                next = true;
                break;
            }

            TodoCoreController.update(id, todo.withDescription(Description.of(inputDescription)));
            TodoController.detail(id);

            next = true;
        } while (!next);
    }

    public static void delete(Todo.TodoId id) {
        final Map<String, Runnable> actions = new HashMap<>() {{
            put("index", TodoController::index);
            put("list", this.get("index"));
            put("detail", () -> TodoController.detail(id));
            put("back", this.get("detail"));
            put("cancel", this.get("detail"));
            put("n", this.get("detail"));
        }};
        boolean next = false;

        final Todo todo = TodoCoreController.get(id);

        do {
            final Map<String, String> response = TodoDelete.display(todo);

            String inputChoice = response.get("choice");

            if (
                    !inputChoice.equalsIgnoreCase("y") &&
                            !actions.containsKey(inputChoice.toLowerCase())
            ) {
                InputInvalid.display();
                continue;
            }

            if (actions.containsKey(inputChoice.toLowerCase())) {
                actions.get(inputChoice.toLowerCase()).run();
                next = true;
                break;
            }

            Boolean isSuccess = TodoCoreController.delete(id);

            if (isSuccess)
                TodoHasDeleted.display(id);
            else
                TodoNotDeleted.display(id);

            TodoController.index();

            next = true;
        } while (!next);
    }

    public static void changeStatus(Todo.TodoId id) {
        Todo todo = TodoCoreController.get(id);

        if (todo.getStatus().equals(Todo.TodoStatus.PENDING))
            setStatusDone(id, todo);
        else
            setStatusPending(id, todo);

        TodoController.detail(id);
    }

    public static void setStatusDone(Todo.TodoId id, Todo todo) {
        TodoCoreController.update(id, todo.withStatus(Todo.TodoStatus.DONE));
    }

    public static void setStatusPending(Todo.TodoId id, Todo todo) {
        TodoCoreController.update(id, todo.withStatus(Todo.TodoStatus.PENDING));
    }
}
