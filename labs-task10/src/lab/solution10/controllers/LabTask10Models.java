package lab.solution10.controllers;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class LabTask10Models {

    public static final class Triangle implements Cloneable, FilesystemObject, Iterable<Triangle.Point> {
        private static Integer instanceCount = 0;
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
        }
        @Override public final String setRecord(char border) {
            return String.valueOf(this.vertex1.x) + border + String.valueOf(this.vertex1.y) + border
                    + String.valueOf(this.vertex2.x) + border + String.valueOf(this.vertex2.y) + border
                    + String.valueOf(this.vertex3.x) + border + String.valueOf(this.vertex3.y);
        }
        public static record Point(Integer x, Integer y) { }
        private final Point vertex1, vertex2, vertex3;
        private final Integer triangleId;
        @NotNull Integer getTriangleId() { return this.triangleId; }

        @NotNull public final Point getVertex1() { return this.vertex1; }
        @NotNull public final Point getVertex2() { return this.vertex2; }
        @NotNull public final Point getVertex3() { return this.vertex3; }

        private Triangle(Point vertex1, Point vertex2, Point vertex3, int id) { super();
            this.vertex1 = vertex1; this.vertex2 = vertex2; this.vertex3 = vertex3;
            this.triangleId = id;
        }
        public Triangle(Point vertex1, Point vertex2, Point vertex3) {
            this(vertex1, vertex2, vertex3, ++Triangle.instanceCount);
        }

        public Triangle clone() throws CloneNotSupportedException {
            super.clone();
            return new Triangle(this.vertex1, this.vertex2, this.vertex3, this.getTriangleId());
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

        @Override public final String toString() {
            return "[ triangleID" + this.getTriangleId() + " ]: [ {" + this.vertex1.x + ", "
                    + this.vertex1.y + "}, {" + this.vertex2.x + ", "
                    + this.vertex2.y + "}, {" + this.vertex3.x + ", " + this.vertex3.y + "} ] = "
                    + this.getArea();
        }
    }

    public static final class Student implements Cloneable, FilesystemObject {

        public static class StudentBuilder implements FilesystemObjectBuilder {
            @Override
            public FilesystemObject decodeRecord(String record) throws Exception {
                var record_part = record.split(" ");
                if(record_part.length != 5) throw new Exception("Бракованные данные");

                return new Student(record_part[0], record_part[1], record_part[2],
                        record_part[3], Integer.parseInt(record_part[4]));
            }
        }
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

        @Override public final String setRecord(char border) {
            return this.getFirstName() + border + this.getLastName() + border + this.getPatronymic()
                + border + this.getGroup() + border + this.getCourse();
        }

        @Override public Student clone() throws CloneNotSupportedException {
            super.clone();
            return Student.fromFIO(this.getFIO(), getGroup(), getCourse());
        }

        @Override public final String toString() {
            return "[ " +  this.getFIO() + "; " + this.getGroup() + "; " + this.getCourse() + " ]";
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
