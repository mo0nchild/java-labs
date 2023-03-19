package lab.solution11.controllers;

import org.jetbrains.annotations.NotNull;
import java.io.*;

public class LabTask11Filesystem implements Serializable {
    public LabTask11Filesystem() { super(); }

    public static String fileDataRead(@NotNull String filepath) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(!(new File(filepath)).exists()) throw new IOException("Файл не найден");

        try (var file_stream = new BufferedReader(new FileReader(filepath))) {
            String line_buffer = "";
            while((line_buffer = file_stream.readLine()) != null) {
                builder.append(line_buffer).append("\n");
            }
        }
        catch (Exception error) { throw new IOException(error.getMessage()); }
        return builder.toString();
    }
}
