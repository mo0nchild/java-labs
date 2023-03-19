package lab.solution10.controllers;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

interface FilesystemObject extends Serializable { public String setRecord(char border); }

interface FilesystemObjectBuilder extends Serializable {
    public FilesystemObject decodeRecord(String record) throws Exception;
}

public class LabTask10Filesystem implements Serializable {
    public LabTask10Filesystem() { super(); }

    public static List<FilesystemObject> fileDataRead(String filepath, FilesystemObjectBuilder builder)
            throws IOException {
        var result = new ArrayList<FilesystemObject>();
        if(!new File(filepath).exists()) throw new IOException("Файл не найден");

        try (var file_stream = new BufferedReader(new FileReader(filepath))) {
            String buffer = null;
            while((buffer = file_stream.readLine()) != null){
                result.add(builder.decodeRecord(buffer));
            }
        }
        catch (Exception error) { throw new IOException(error.getMessage()); }
        return result;
    }

    public static void fileDataWrite(String filepath, @NotNull FilesystemObject[] data) throws IOException {
        if(filepath == null) throw new IOException("Нет данных");
        var file_descriptor = new File(filepath);
        if (!file_descriptor.exists()) {
            if (!file_descriptor.createNewFile()) throw new IOException("Файл не подготовлен");
        }
        try (var file_stream = new BufferedWriter(new FileWriter(filepath))) {
            for(var item : data) file_stream.write(item.setRecord(' ') + "\n");
        }
        catch (IOException error) { throw error; }
    }

}
