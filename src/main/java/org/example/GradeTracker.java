package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Трекер оценок
 */
public class GradeTracker {
    private final Map<Integer, Student> students;
    private int studentCounter = 1;

    /**
     * Конструктор трекера
     * @param newStudents флаг создания нового файла для студентов
     */
    public GradeTracker(boolean newStudents) {

        this.students = newStudents ? new HashMap<>() : FileHelper.loadFromStudents();
        this.studentCounter = students.size() + 1;
    }
    public GradeTracker(Map<Integer, Student> students) {
        System.out.println("Загрузить файл с данными студентов? (1-да,2-нет)");
        this.students = students;
        this.studentCounter = students.size() + 1;
    }
    /**
     * Добавить студента
     *
     * @param name имя студента
     */
    public void addStudent(String name) {
        var student = new Student(name);
        this.students.put(studentCounter, student);
        studentCounter++;
        System.out.println(
                String.format("Студент %s добавлен, общее количество студентов %s", name, getStudentCount()));
    }

    /**
     * Удалить студента
     *
     * @param id студента
     */
    public void removeStudent(Integer id) {
        getStudent(id).ifPresent(value -> {
            students.remove(id);
            System.out.println(
                    String.format("Студент %s удален, общее количество студентов %s", id, getStudentCount()));
        });
    }


    /**
     * Добавить оценку студенту
     *
     * @param id     студента
     * @param grades оценка
     */
    public void addGrade(Integer id, Integer grades) {
        getStudent(id).ifPresent(value -> value.addGrade(grades));
    }

    /**
     * Обновить оценку
     *
     * @param id     студента
     * @param index  порядковый номер оценки
     * @param grabes оценка
     */
    public void updateGrabe(Integer id, Integer index, Integer grabes) {
        getStudent(id).ifPresent(value -> value.updateGrabe(index, grabes));
    }

    /**
     * Отобразить всех студентов и их оценки
     */
    public void displayAllGrades() {
        students.forEach((key, student) -> {
            System.out.println(
                    String.format("Студент:%s - %s\nОценки: %s", key, student.getName(), student.getGrades())
            );
        });
    }

    /**
     * Отобразить оценки студента
     *
     * @param id студента
     */
    public void displayGrades(Integer id) {
        getStudent(id).ifPresent(value ->
                System.out.println(String.format("Студент:%s\nОценки: %s", value.getName(), value.getGrades()))
        );
    }

    /**
     * Получить студента по Id
     *
     * @param id студента
     * @return студент
     */
    private Optional<Student> getStudent(Integer id) {
        var student = Optional.ofNullable(this.students.get(id));
        if (student.isEmpty()) {
            System.out.println(String.format("Студент с id %s не найден", id));
        }
        return student;
    }

    /**
     * Сохранить студентов перед закрытием программы
     */
    public void saveOnExit() {
        FileHelper.saveToStudents(this.students);
    }

    /**
     * Получить количество студентов
     *
     * @return количество студентов
     */
    private Integer getStudentCount() {
        return this.students.size();
    }
}
