package org.example;

public enum Command {
    ADD_STUDENT("Добавить студента"),
    REMOVE_STUDENT("Удалить студента"),
    INFO_STUDENT("Информация о студенте");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
