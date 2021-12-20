package id.luthfisolahudin.smkn4.pbo.todolist.core.model;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Name {
    @NonNull
    String value;
}
