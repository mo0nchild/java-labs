package lab.solution10.controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

interface FilesystemObject extends Serializable { public String setRecord(char border); }

interface FilesystemObjectBuilder extends Serializable {
    public FilesystemObject decodeRecord(String record) throws Exception;
    public <TObject extends FilesystemObject> boolean checkCombine();
}

public class LabTask10Filesystem implements Serializable {
    public LabTask10Filesystem() { super(); }

    public static <TResult extends FilesystemObject> List<TResult> fileDataRead(
            String filepath, FilesystemObjectBuilder builder) throws IOException {
        if (builder.checkCombine())
        List<TResult> result = null;
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

}
