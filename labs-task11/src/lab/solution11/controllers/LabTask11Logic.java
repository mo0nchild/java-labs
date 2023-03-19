package lab.solution11.controllers;

import labs.helper.types.LabTaskException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class LabTask11Logic implements Serializable {
    public LabTask11Logic() { super(); }

    public final String calculateTask1(@NotNull String text) throws LabTaskException {
        var regex_text = Pattern.compile("(\\w)+\\s*=\\s*(\\w)+\\s*\\+\\s*1\\s*;");
        var matcher_text = regex_text.matcher(text);

        var splited_text = regex_text.split(text);
        if(splited_text.length < 1) throw new LabTaskException("Соответствие не найдено", this.getClass());

        StringBuilder result = new StringBuilder(splited_text[0]);
        for(int index = 1; matcher_text.find(); index++) {
            var item = matcher_text.group();
            var names_list = item.split("\\s*([=+;])\\s*");

            if(names_list[0].equals(names_list[1])) result.append(names_list[0]).append("++;");
            else result.append(item);
            result.append(splited_text[index]);
        }
        return result.toString();
    }

    public final String[] calculateTask2(@NotNull String text, int length) throws LabTaskException {
        if(length < 1) throw new LabTaskException("Недопустимая длина", this.getClass());
        var text_list = text.split("\\s*(\\s|!|,|\\.)\\s*");

        var result_list = new ArrayList<String>();
        for(var item : text_list) {
            if(item.length() == length) result_list.add(item);
        }
        return result_list.toArray(String[]::new);
    }
}
