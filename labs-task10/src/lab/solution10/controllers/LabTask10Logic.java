package lab.solution10.controllers;

import labs.helper.types.LabTaskException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LabTask10Logic implements Serializable {

    public LabTask10Logic() { super(); }

    public final Integer calculateTask1(LabTask10Models.Triangle[] triangles) throws LabTaskException {
        var result_count = 0;
        for(int index = 0; index < triangles.length - 1; index++) {
            for(int k = index + 1; k < triangles.length; k++) {

                if(triangles[index].getArea() >= triangles[k].getArea()) continue;
                try {
                    var buffer_triangle = triangles[index].clone();
                    triangles[index] = triangles[k];
                    triangles[k] = buffer_triangle;
                }
                catch (CloneNotSupportedException error) {
                    throw new LabTaskException(error.getMessage(), this.getClass());
                }
                result_count++;
            }
        }
        return result_count;
    }

    public final void calculateTask2(LabTask10Models.Student[] students) throws LabTaskException {
        var full_comparator = new LabTask10Models.Student.StudentCourseComparator()
                .thenComparing(new LabTask10Models.Student.StudentGroupComparator())
                .thenComparing(new LabTask10Models.Student.StudentFIOComparator());
        List<LabTask10Models.Student> result = null;
        try {
            result = Arrays.stream(students).sorted(full_comparator).toList();
        }
        catch (RuntimeException error) { throw new LabTaskException(error.getMessage(), this.getClass()); }
        for(int index = 0; index < students.length; index++)  students[index] = result.get(index);
    }

}
