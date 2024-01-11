package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Помощник с файлом
 */
public class FileHelper {
    private static final String DATA_FILE_NAME = "students_data.txt";

    /**
     * Загрузить данные
     *
     * @return список студентов
     */
    public static Map<Integer, Student> loadFromStudents() {
        try {
            var ois = new ObjectInputStream(new FileInputStream(DATA_FILE_NAME));
            return (Map<Integer, Student>) ois.readObject();
        } catch (Exception e) {
            System.out.printf("ERROR: Ошибка чтение файла: ", e.getMessage());
        }
        return new HashMap<>();
    }

    /**
     * Сохранить данные
     *
     * @param students список студентов
     */
    public static void saveToStudents(Map<Integer, Student> students) {
        try {
            var oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME));
            oos.writeObject(students);
        } catch (Exception e) {
            System.out.printf("ERROR: Ошибка сохранения файла: ", e.getMessage());
        }
    }
}
