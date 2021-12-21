package id.luthfisolahudin.smkn4.pbo.todolist.core.model;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Description {
    String value;

    public Boolean isBlank() {
        return StringUtils.isBlank(value);
    }
}
