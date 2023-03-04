package lab.solution9.controllers;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class LabTask9Filesystem implements Serializable {

    private LabTask9Filesystem() { super(); }
    public static record FileData(List<Integer> list1, List<Integer> list2, List<Integer> list) {
        public boolean isAnyEmpty() { return list1.isEmpty() || list2.isEmpty() || list.isEmpty(); }
    }
    public static void fileDataWrite(final String filepath, @NotNull FileData data) throws IOException {
        if(data.isAnyEmpty() || filepath == null) throw new IOException("Нет данных");

        var file_descriptor = new File(filepath);
        if (!file_descriptor.exists()) {
            if (!file_descriptor.createNewFile()) throw new IOException("Файл не подготовлен");
        }
        try (var file_stream = new DataOutputStream(new FileOutputStream(filepath))) {
            file_stream.writeInt(data.list1().size()); file_stream.writeInt(data.list2().size());
            file_stream.writeInt(data.list().size());

            for(var item : data.list1()) file_stream.writeInt(item);
            for(var item : data.list2()) file_stream.writeInt(item);
            for(var item : data.list()) file_stream.writeInt(item);
        }
        catch (IOException error) { System.out.println(error.getMessage()); }
    }

    public static FileData fileDataRead(final String filepath) throws IOException {
        FileData file_result = null;
        if(!new File(filepath).exists()) throw new IOException("Файл не найден");

        try (var file_stream = new DataInputStream(new FileInputStream(filepath))) {
            int size1 = file_stream.readInt(), size2 = file_stream.readInt(),
                    size = file_stream.readInt();
            List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>(), list = new ArrayList<>();

            for (int index = 0; index < size1; index++) list1.add(file_stream.readInt());
            for (int index = 0; index < size2; index++) list2.add(file_stream.readInt());
            for (int index = 0; index < size; index++) list.add(file_stream.readInt());
            file_result = new FileData(list1, list2, list);
        }
        catch (IOException error) { System.out.println(error.getMessage()); }
        return file_result;
    }

}
