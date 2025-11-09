package org.example;

import org.example.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.io.InputStream;




public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            String response;

            switch (messageText) {
                case "/hello":
                    response = new HelloCommand().getResponse();
                    break;
                case "/start":
                    response = new HelloCommand().getResponse();
                    break;
                case "/quote":
                    response = new QuoteCommand().getResponse();
                    break;
                case "/goodbye":
                    response = new GoodbyeCommand().getResponse();
                    break;
                case "/help":
                    response = new HelpCommand().getResponse();
                    break;
                case "/author":
                    response = new AuthorCommand().getResponse();
                    break;
                default:
                    response = "Извини, я не знаю такую команду. Напиши /help для списка доступных команд.";
                    break;

            }

            sendMessage(chatId, response);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotName"; // Название бота из Telegram
    }

    @Override
    public String getBotToken() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("token.txt");
            if (inputStream == null) {
                throw new IOException("token.txt not found in resources!");
            }
            return new String(inputStream.readAllBytes()).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}