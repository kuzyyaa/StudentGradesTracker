package org.example;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.function.Function;

/**
 * Элемент меню
 */
@Value
@RequiredArgsConstructor
public class MenuItem {
    /**
     * Описание
     */
    String description;
    /**
     * Функция
     */
    MenuFunction function;
}
