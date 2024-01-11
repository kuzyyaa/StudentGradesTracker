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
        if (grabe >= 1 && grabe <= 5) {
            this.grades.add(grabe);
            System.out.println(String.format("Для студента %s добавлена оценка %s", this.name, grabe));
        } else {
            System.out.println(
                    String.format("ERROR: Оценка %s для студента %s не является допустимой , диапазон оценок от 1 до 5",
                            grabe, this.name));
        }
    }

    /**
     * Обновить оценку
     *
     * @param index порядковый номер оценки
     * @param grabe оценка
     */
    public void updateGrabe(Integer index, Integer grabe) {
        if (index < 0 || index >= this.grades.size()) {
            System.out.println(
                    String.format("ERROR: Для студента %s указан некорректный индекс оценки.", this.name)
            );
        } else {
            var studentGrade = this.grades.get(index);
            this.grades.set(index, grabe);
            System.out.println(
                    String.format("Для студента %s обновлена оценка %s на %s", this.name, studentGrade, grabe)
            );
        }
    }
}
