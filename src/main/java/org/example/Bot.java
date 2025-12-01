package org.example;

import org.example.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    private final Map<String, Command> commands = new HashMap<>();

    public Bot() {
        commands.put("/start", new HelloCommand());
        commands.put("/hello", new HelloCommand());
        commands.put("/goodbye", new GoodbyeCommand());
        commands.put("/help", new HelpCommand());
        commands.put("/discord", new DiscordCommand());
        commands.put("/dota2", new Dota2Command());
        commands.put("/brawl", new BrawlStarsCommand());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            Command cmd = commands.get(messageText);
            String response = (cmd != null)
                    ? cmd.getResponse()
                    : "❓ Не знаю такую команду. Напиши /help.";

            sendMessage(chatId, response);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.setParseMode("Markdown");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotName";
    }

    @Override
    public String getBotToken() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("token.txt");
            if (inputStream == null) throw new IOException("token.txt not found!");

            return new String(inputStream.readAllBytes()).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
