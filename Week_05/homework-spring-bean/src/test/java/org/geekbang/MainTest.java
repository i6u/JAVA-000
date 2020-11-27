package org.geekbang;

import org.geekbang.entity.Student;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MainTest {

    @Test
    public void fun() {
        BiFunction<Integer, String, Student> biFunction = Student::new;
        BiFunction<Integer, String, Student> t_biFunction = (id, name) -> new Student(id, name);
        BiFunction<Integer, String, Student> t1_biFunction = new BiFunction<Integer, String, Student>() {
            @Override
            public Student apply(Integer id, String name) {
                return new Student(id, name);
            }
        };

        System.out.println(biFunction.getClass().getName());
        BiFunction<Integer, String, String> integerStringStringBiFunction = biFunction.andThen(Student::toString);
        System.out.println(integerStringStringBiFunction.getClass());
        System.out.println(integerStringStringBiFunction.apply(2, "t"));
        Student student = biFunction.apply(1, "Witt");
        System.out.println(student.toString());

        Supplier<Student> supplier = Student::new;
        Supplier<Student> t_supplier = () -> new Student();
        Supplier<Student> t1_supplier = new Supplier<Student>() {
            @Override
            public Student get() {
                return new Student();
            }
        };

        System.out.println(supplier.getClass().getName());
        Student student1 = supplier.get();
        System.out.println(student1);

    }
}