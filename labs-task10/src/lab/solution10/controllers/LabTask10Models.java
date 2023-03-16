package lab.solution10.controllers;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class LabTask10Models {

    public static final class Triangle implements FilesystemObject, Iterable<Triangle.Point> {

        public static class TriangleBuilder implements FilesystemObjectBuilder {
            @Override
            public final FilesystemObject decodeRecord(String record) throws Exception {
                var record_part = record.split(" ");
                if(record_part.length != 6) throw new Exception("Данные бракованные");
                return new Triangle(
                        new Point(Integer.parseInt(record_part[0]), Integer.parseInt(record_part[1])),
                        new Point(Integer.parseInt(record_part[2]), Integer.parseInt(record_part[3])),
                        new Point(Integer.parseInt(record_part[4]), Integer.parseInt(record_part[5]))
                );
            }

            @Override
            public <TObject extends FilesystemObject> boolean checkCombine() {
                return TObject.ca == Triangle::getClass;
            }

        }

        @Override
        public String setRecord(char border) {
            return null;
        }

        public static record Point(Integer x, Integer y) { }
        private final Point vertex1, vertex2, vertex3;

        @NotNull public final Point getVertex1() { return this.vertex1; }
        @NotNull public final Point getVertex2() { return this.vertex2; }
        @NotNull public final Point getVertex3() { return this.vertex3; }

        public Triangle(Point vertex1, Point vertex2, Point vertex3) { super();
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.vertex3 = vertex3;
        }

        public Triangle clone() throws CloneNotSupportedException {
            super.clone();
            return new Triangle(this.getVertex1(), this.getVertex2(), this.getVertex3());
        }

        private static Double getSideLength(Point a, Point b) {
            return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        }

        public final Double getArea() {
            var side_a = Triangle.getSideLength(this.vertex1, this.vertex2);
            var side_b = Triangle.getSideLength(this.vertex2, this.vertex3);
            var side_c = Triangle.getSideLength(this.vertex1, this.vertex3);

            var p = (side_a + side_b + side_c) / 2;
            return Math.sqrt(p * (p - side_a) * (p - side_b) + (p - side_c));
        }

        @NotNull @Override
        public Iterator<Point> iterator() { return new TriangleIterator(); }

        private final class TriangleIterator implements Iterator<Point> {
            private final Point[] vertexes;
            private Integer currentIndex = 0;

            public final Point[] getIntegers() { return this.vertexes; }
            public final Integer getCurrentIndex() { return this.currentIndex; }

            public TriangleIterator() { super();
                this.vertexes = new Point[] {
                    Triangle.this.vertex1, Triangle.this.vertex2, Triangle.this.vertex3
                };
            }
            @Override public boolean hasNext() { return vertexes.length > this.currentIndex; }
            @Override public Point next() { return this.vertexes[this.currentIndex++]; }
        }
    }

    public static final class Student implements Serializable {
        private final String lastName, firstName, patronymic, group;
        private final Integer course;

        @NotNull public final String getLastName() { return this.lastName; }
        @NotNull public final String getPatronymic() { return this.patronymic; }
        @NotNull public final String getFirstName() { return this.firstName; }

        @NotNull public final String getGroup() { return this.group; }
        @NotNull public final Integer getCourse() { return this.course; }

        public final String getFIO() { return this.lastName + " " + this.firstName
                + " " + this.patronymic; }

        public Student(String lastName, String firstName, String patronymic,
                       String group, Integer course) {
            this.course = course;
            this.lastName = lastName;
            this.patronymic = patronymic;
            this.firstName = firstName;
            this.group = group;
        }

        public static Student fromFIO(String FIO, String group, Integer course) {
            var fio_parts = FIO.split(" ");
            return new Student(fio_parts[0], fio_parts[1], fio_parts[2], group, course);
        }

        public static final class StudentFIOComparator implements Comparator<Student> {
            @Override
            public final int compare(@NotNull Student o1, @NotNull Student o2) {
                return o1.getFIO().compareTo(o2.getFIO());
            }
        }

        public static final class StudentCourseComparator implements Comparator<Student> {
            @Override
            public final int compare(@NotNull Student o1, @NotNull Student o2) {
                return o1.getCourse().compareTo(o2.getCourse());
            }
        }

        public static final class StudentGroupComparator implements Comparator<Student> {
            @Override
            public final int compare(@NotNull Student o1, @NotNull Student o2) {
                return o1.getGroup().compareTo(o2.getGroup());
            }
        }
    }

}
