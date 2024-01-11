package org.example;

import java.util.HashMap;
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
    private final Map<Integer, MenuItem> menuItems = new HashMap<>();

    public Menu() {
        System.out.println(DELIMITER);
        var command = getCommand("Загрузить файл с данными о студентах? (1-да,2-нет): ");
        tracker = new GradeTracker(command.equals("1"));
        menuItems.put(ADD_STUDENT_OPTION, new MenuItem("Добавить студента", this::addStudent));
        menuItems.put(REMOVE_STUDENT_OPTION, new MenuItem("Удалить студента", this::removeStudent));
        menuItems.put(SHOW_INFO_OPTION, new MenuItem("Информация о студенте", this::showStudentInfo));
        menuItems.put(DISPLAY_ALL_OPTION, new MenuItem("Отобразить всех студентов", this::displayAllStudents));
        menuItems.put(EXIT_OPTION, new MenuItem("Выход", this::exit));
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(tracker::saveOnExit));

        while (true) { //NOSONAR
            System.out.println(DELIMITER);

            displayMenu();

            try {
                var option = Integer.parseInt(getCommand("Выберите опцию:  "));
                System.out.println(DELIMITER);
                executeFunction(option);
            } catch (Exception e) {
                System.out.println(DELIMITER);
                System.out.printf("ERROR: %s", e.getMessage());
            }
        }
    }

    /**
     * Отобразить меню
     */
    private void displayMenu() {
        System.out.println("Меню:");
        menuItems.forEach((key, item) -> System.out.println(String.format("%s. %s", key, item.getDescription())));
    }

    /**
     * Закрытие
     */
    private void exit() {
        System.out.println("Закрытие программы!");
        scanner.close();
        System.exit(0);
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
            if (tracker.getStudent(studentId).isEmpty()) {
                return;
            }
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

    /**
     * Выполнить функцию
     *
     * @param key ключ по которому можно найти функцию
     */
    public void executeFunction(int key) {
        var function = menuItems.get(key);
        if (function != null) {
            function.getFunction().apply();
        } else {
            System.out.println("Не удалось распознать команду, попробуйте еще раз.");
        }
    }
}
