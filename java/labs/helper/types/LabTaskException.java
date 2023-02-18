package labs.helper.types;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class LabTaskException extends Exception {
    private String task_name = "";
    public LabTaskException(String message, Class<?> lab_task) {
        super("[Task=" + lab_task.getName() + "]: " + message);
        this.task_name = lab_task.getName();
    }

    public String getTaskName() { return task_name; }
}
