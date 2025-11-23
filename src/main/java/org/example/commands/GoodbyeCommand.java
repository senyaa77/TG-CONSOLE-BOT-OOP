package org.example.commands;

public class GoodbyeCommand implements Command {

    @Override
    public String getResponse() {
        return "👋 До встречи!";
    }
}
