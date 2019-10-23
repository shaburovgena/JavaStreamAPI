package com.example;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

public class APIExamples {

    private List<Employee> emps = List.of(
            new Employee("Michael", "Smith", 243, 43, Position.CHEF),
            new Employee("Jane", "Smith", 523, 40, Position.MANAGER),
            new Employee("Jury", "Gagarin", 6423, 26, Position.MANAGER),
            new Employee("Jack", "London", 5543, 53, Position.WORKER),
            new Employee("Eric", "Jackson", 2534, 22, Position.WORKER),
            new Employee("Andrew", "Bosh", 3456, 44, Position.WORKER),
            new Employee("Joe", "Smith", 723, 30, Position.MANAGER),
            new Employee("Jack", "Gagarin", 7423, 35, Position.MANAGER),
            new Employee("Jane", "London", 7543, 42, Position.WORKER),
            new Employee("Mike", "Jackson", 7534, 31, Position.WORKER),
            new Employee("Jack", "Bosh", 7456, 54, Position.WORKER),
            new Employee("Mark", "Smith", 123, 41, Position.MANAGER),
            new Employee("Jane", "Gagarin", 1423, 28, Position.MANAGER),
            new Employee("Sam", "London", 1543, 52, Position.WORKER),
            new Employee("Jack", "Jackson", 1534, 27, Position.WORKER),
            new Employee("Eric", "Bosh", 1456, 32, Position.WORKER)
    );

    private List<Department> deps = List.of(
            new Department(1, 0, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 1, "East"),
            new Department(4, 2, "Germany"),
            new Department(5, 2, "France"),
            new Department(6, 3, "China"),
            new Department(7, 3, "Japan")
    );

    @Test
    public void creationExamples() throws IOException {

        Stream<String> lines = Files.lines(Paths.get("someStream.txt"));
        Stream<Path> list = Files.list(Paths.get("./"));
        Stream<Path> walk = Files.walk(Paths.get("./"), 3);

        IntStream intStream = IntStream.of(1, 2, 3, 4);
        DoubleStream doubleStream = DoubleStream.of(1, 2, 3, 4);
        IntStream range = IntStream.range(10, 100); //10 ... 99
        IntStream intStream1 = IntStream.rangeClosed(10, 100); //10 ... 100

        int[] ints = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(ints);

        Stream<String> stringStream = Stream.of("1", "2", "3");
        Stream<? extends Serializable> streamSerializable = Stream.of(1, 2.1, "3");

        Stream<String> build = Stream.<String>builder()
                .add("one")
                .add("two")
                .add("three")
                .build();
    }

    @Test
    public void creationFromCollectionsExamples() throws IOException {
        Stream<Employee> stream = emps.stream();
        Stream<Employee> employeeStream = emps.parallelStream();

        Stream.concat(stream, employeeStream);
    }

    @Test
    public void creationAsGenerationExamples() throws IOException {
        Stream<Event> generate = Stream.generate(() ->
                new Event(UUID.randomUUID(), LocalDateTime.now(), ""));
    }

    @Test
    public void creationAsIterationExamples() throws IOException {
        Stream<Integer> iterate = Stream.iterate(1950, val -> val + 3);

    }

    @Test
    public void methodsExamples() {
        Stream<Employee> stream = emps.stream();
        stream.count();
        Stream<Employee> streamForEach = emps.stream();
        streamForEach.forEach(employee -> System.out.println(employee.getAge()));
        emps.forEach(employee -> System.out.println(employee.getId()));
        emps.stream().forEachOrdered(employee -> System.out.println(employee.getAge()));

        List<Employee> collect = emps.stream().collect(Collectors.toList());
        Object[] objects = emps.stream().toArray();
        Map<Integer, String> empsMap = emps.stream().collect(Collectors.toMap(
                employee -> employee.getId(),
                employee -> String.format("%s %s", employee.getFirstName(), employee.getLastName())
        ));

        System.out.println(collect.get(0).getFirstName());
        System.out.println(empsMap.get(243));

        IntStream intStream = IntStream.of(100, 200, 300, 400);
        OptionalInt reduceResult = intStream.reduce(((left, right) -> left + right));
        System.out.println(reduceResult.getAsInt());

    }

    @Test
    public void mathExamples() {
        System.out.println(IntStream.of(100, 200, 300, 400).average());
        System.out.println(IntStream.of(100, 200, 300, 400).max());
        System.out.println(IntStream.of(100, 200, 300, 400).min());
        System.out.println(IntStream.of(100, 200, 300, 400).sum());
        System.out.println(IntStream.of(100, 200, 300, 400).summaryStatistics());
        Optional<Employee> maxAgeWithComparator = emps.stream().max(Comparator.comparingInt(Employee::getAge));
        System.out.println(maxAgeWithComparator.get().getFirstName());

        Optional<Employee> maxAgeWithLambda = emps.stream().max((emloyee1, employee2) -> emloyee1.getAge() - employee2.getAge());
        System.out.println(maxAgeWithLambda.get().getFirstName());

        Optional<Employee> randomEmployee = emps.stream().findAny();
        System.out.println(randomEmployee.get().getFirstName());
        Optional<Employee> firstEmployee = emps.stream().findFirst();
        System.out.println(firstEmployee.get().getFirstName());


        boolean isLessSixteYO = emps.stream().noneMatch((employee -> employee.getAge() > 60)); //true
        boolean isOlderEighteenYO = emps.stream().allMatch((employee -> employee.getAge() > 18)); //true
        boolean isEmplShef = emps.stream().anyMatch((employee -> employee.getPosition() == Position.CHEF)); //true

    }

    @Test
    public void transform() {
        LongStream longStream = IntStream.of(100, 200, 300, 400).mapToLong(Long::valueOf);

        Stream<Event> eventStream = IntStream.of(100, 200, 300, 400).mapToObj(value ->
                new Event(UUID.randomUUID(), LocalDateTime.of(value, 12, 1, 12, 0), ""));
        eventStream.forEachOrdered(event -> System.out.println(event.getTimeTag()));

        Stream<Employee> skipThreeEmployee = emps.stream().skip(3);
        Stream<Employee> limitFirstFiveEmployee = emps.stream().limit(5);

        emps.stream()
                .skip(3)
                .limit(5).forEachOrdered(employee -> System.out.println(employee.getFirstName()));

        System.out.println();
        emps.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))/*(employee1, employee2) -> employee1.getAge()-employee2.getAge()*/
                .forEach(System.out::println);


        System.out.println();
        emps.stream()
                .takeWhile(employee -> employee.getAge() > 30).forEach(System.out::println);//Стрим отрабатывает пока не совпадет условие

        System.out.println();
        emps.stream()
                .dropWhile(employee -> employee.getAge() > 30).forEach(System.out::println);//Стрим не отрабатывает пока не совпадет условие

        System.out.println();
        emps.stream()
                .peek(employee -> employee.setAge(18)).forEach(System.out::println);//Заменяем всем элементам определенное значение

        System.out.println();
        IntStream.of(100, 200, 300, 400)
                .flatMap(value -> IntStream.of(value - 50, value)).forEach(System.out::println);//Добавляем промежуточные значения
    }

    @Test
    public void realExample() {
        Stream<Employee> filterSortedLimitEmpls = emps.stream()
                .filter(employee -> employee.getAge() < 40 && employee.getPosition() != Position.WORKER)
                .sorted(Comparator.comparingInt(Employee::getAge))
                .limit(4);
        print(filterSortedLimitEmpls);

        Stream<Employee> filterSortedLimitEmplsOldest = emps.stream()
                .filter(employee -> employee.getAge() > 40)
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .limit(5);
        print(filterSortedLimitEmplsOldest);

        IntSummaryStatistics intSummaryStatistics = emps.stream()
                .mapToInt(Employee::getAge)
                .summaryStatistics();

        System.out.println(intSummaryStatistics);

    }

    private void print(Stream<Employee> stream) {
        stream
                .map(emp -> String.format(
                        "%4d | %-15s %-10s age %s %s",
                        emp.getId(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getAge(),
                        emp.getPosition()
                ))
                .forEach(System.out::println);

        System.out.println();
    }

    @Test
    public void reduceExample() {
        System.out.println(deps.stream().reduce(this::reducer));
    }

    public Department reducer(Department parent, Department child) {
        if (child.getParent() == parent.getId()) {
            parent.getChild().add(child);
        } else {
            parent.getChild().forEach(subParent -> reducer(subParent, child));
        }

        return parent;
    }

}
