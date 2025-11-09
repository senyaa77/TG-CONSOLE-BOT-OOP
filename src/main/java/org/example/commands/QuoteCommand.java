package org.example.commands;

import java.util.Random;

public class QuoteCommand {

    private static final String[] QUOTES = {
            "«Путь мудрости лежит через ошибки.»",
            "«Тот, кто хочет — ищет возможности, кто не хочет — ищет причины.»",
            "«Сила не в том, чтобы никогда не падать, а в том, чтобы подниматься каждый раз.»",
            "«Каждый день — это новая возможность стать лучше, чем вчера.»",
            "«Мы становимся тем, о чём больше всего думаем.»"
    };

    private final Random random = new Random();

    public String getResponse() {
        int index = random.nextInt(QUOTES.length); // случайный индекс от 0 до 2
        return QUOTES[index];
    }
}
