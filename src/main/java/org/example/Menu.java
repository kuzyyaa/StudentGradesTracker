package org.example;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class Menu {
    private static final int ADD_STUDENT_OPTION = 1;
    private static final int REMOVE_STUDENT_OPTION = 2;
    private static final int SHOW_INFO_OPTION = 3;
    private static final int DISPLAY_ALL_OPTION = 4;
    private static final int EXIT_OPTION = 5;

    private static final String DELIMITER = "\n======================================\n";

    private final GradeTracker tracker;
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, String> menuMap = new LinkedHashMap<>();

    public Menu() {
        System.out.println(DELIMITER);
        var command = getCommand("Загрузить файл с данными о студентах? (1-да,2-нет): ");
        tracker = command.equals("1") ? new GradeTracker(true) : new GradeTracker(false);
        menuMap.put(ADD_STUDENT_OPTION, "Добавить студента");
        menuMap.put(REMOVE_STUDENT_OPTION, "Удалить студента");
        menuMap.put(SHOW_INFO_OPTION, "Информация о студенте");
        menuMap.put(DISPLAY_ALL_OPTION, "Отобразить всех студентов");
        menuMap.put(EXIT_OPTION, "Выход");
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(tracker::saveOnExit));

        while (true) { //NOSONAR
            System.out.println(DELIMITER);

            displayMenu();

            var command = Integer.parseInt(getCommand("Выберите опцию:  "));
            switch (command) {
                case ADD_STUDENT_OPTION:
                    System.out.println(DELIMITER);
                    addStudent();
                    break;
                case REMOVE_STUDENT_OPTION:
                    System.out.println(DELIMITER);
                    removeStudent();
                    break;
                case SHOW_INFO_OPTION:
                    System.out.println(DELIMITER);
                    showStudentInfo();
                    break;
                case DISPLAY_ALL_OPTION:
                    System.out.println(DELIMITER);
                    displayAllStudents();
                    break;
                case EXIT_OPTION:
                    System.out.println(DELIMITER);
                    System.out.println("Закрытие программы!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(DELIMITER);
                    System.out.println("Не удалось распознать команду, попробуйте еще раз.");
            }
        }
    }

    /**
     * Отобразить меню
     */
    private void displayMenu() {
        System.out.println("Меню:");
        menuMap.forEach((key, name) -> System.out.println(String.format("%s. %s", key, name)));
    }

    /**
     * Процесс добавление студента
     */
    private void addStudent() {
        System.out.println("Добавить студента:");
        System.out.println("Для выхода в меню введите -1");
        var command = getCommand("Введите имя нового студента: ");
        if (checkForExit(command)) {
            return;
        }
        tracker.addStudent(command);
    }

    /**
     * Процесс удаление студента
     */
    private void removeStudent() {
        System.out.println("Удалить студента:");
        System.out.println("Для выхода в меню введите -1");
        var command = getCommand("Введите ID студента для удаления:");
        if (checkForExit(command)) {
            return;
        }
        tracker.removeStudent(Integer.parseInt(command));
    }

    /**
     * Процесс отображения информации о студенте
     */
    private void showStudentInfo() {
        System.out.println("Информация о студенте:");
        System.out.println("Для выхода в меню введите -1");
        var command = getCommand("Введите ID студента: ");
        if (checkForExit(command)) {
            return;
        }
        var studentId = Integer.parseInt(command);

        while (true) { //NOSONAR
            tracker.displayGrades(studentId);

            System.out.println(DELIMITER);
            System.out.println("Доступные опции для студента:");
            System.out.println("1. Обновить оценку");
            System.out.println("2. Добавить оценку");
            System.out.println("3. Обратно в главное меню");

            command = getCommand("Выберите опцию: ");
            switch (command) {
                case "1":
                    updateGrade(studentId);
                    break;
                case "2":
                    addGrade(studentId);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Не удалось распознать команду , попробуйте еще раз.");
            }
        }
    }

    /**
     * Процесс обновление оценки
     *
     * @param id студента
     */
    private void updateGrade(Integer id) {
        System.out.println("Обновить оценку:");
        System.out.println("Для выхода в меню введите -1");
        var command = getCommand("Введите индекс оценки: ");
        if (checkForExit(command)) {
            return;
        }
        var index = Integer.parseInt(command);
        command = getCommand("Введите новое значение оценки: ");
        var grabe = Integer.parseInt(command);

        tracker.updateGrabe(id, index, grabe);
    }

    /**
     * Процесс добавление оценки
     *
     * @param id студент
     */
    private void addGrade(Integer id) {
        System.out.println("Добавить оценку:");
        System.out.println("Для выхода в меню введите -1");
        var command = getCommand("Введите новую оценку: ");
        if (checkForExit(command)) {
            return;
        }
        var grabe = Integer.parseInt(command);
        tracker.addGrade(id, grabe);
    }

    /**
     * Отобразить всех студентов
     */
    private void displayAllStudents() {
        System.out.println("Список студентов:");
        tracker.displayAllGrades();
    }

    /**
     * Получить команду
     *
     * @param text информация что за поле для команды.
     * @return команда
     */
    private String getCommand(String text) {
        System.out.print(text);
        return scanner.nextLine().trim();
    }

    /**
     * Проверка команды на выход
     *
     * @param command командна
     * @return true если команда равна -1
     */
    private boolean checkForExit(String command) {
        return command.equals("-1");
    }
}
