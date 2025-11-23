package org.example.commands;

public class HelloCommand implements Command {

    @Override
    public String getResponse() {
        return "👋 Привет! Я игровой статус-бот.\nПопробуй /discord или /help.";
    }
}
