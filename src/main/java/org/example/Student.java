package org.example;

import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Студент
 */
@Value
public class Student implements Serializable {

    /**
     * Имя студента
     */
    String name;

    /**
     * Оценки студента
     */
    List<Integer> grades = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    /**
     * Добавить оценку
     *
     * @param grabe оценка
     */
    public void addGrade(Integer grabe) {
        this.grades.add(grabe);
        System.out.println(String.format("Для студента %s добавленна оценка %s", this.name, grabe));
    }

    /**
     * Обновить оценку
     *
     * @param index порядковый номер оценки
     * @param grabe оценка
     */
    public void updateGrabe(Integer index, Integer grabe) {
        if (this.grades.isEmpty()) {
            System.out.println(
                    String.format("Для студента %s отсутствуют оценки , поэтому нет возможности обновить.", this.name)
            );
        } else {
            var studentGrades = this.grades.get(index);
            this.grades.set(index, grabe);
            System.out.println(
                    String.format("Для студента %s обновлена оценка %s на %s", this.name, studentGrades, grabe)
            );
        }
    }
}
