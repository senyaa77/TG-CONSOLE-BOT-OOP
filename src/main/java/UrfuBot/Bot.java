package UrfuBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.List;


import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    private final Map<String, Commands.Command> commands = new HashMap<>();
    private Users users = new Users();

    public Bot() {
        commands.put("/start", new Commands.StartCommand());
        commands.put("/hello", new Commands.HelloCommand());
        commands.put("/goodbye", new Commands.GoodbyeCommand());
        commands.put("/help", new Commands.HelpCommand());
        commands.put("/discord", new Commands.DiscordCommand());
        commands.put("/dota2", new Commands.Dota2Command());
        commands.put("/brawl", new Commands.BrawlStarsCommand());
    }

    private ReplyKeyboardMarkup getMainKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true); // клавиатура под размер экрана
        keyboardMarkup.setOneTimeKeyboard(false); // клавиатура не исчезает после нажатия

        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строка кнопок
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/start"));
        row1.add(new KeyboardButton("/hello"));
        row1.add(new KeyboardButton("/goodbye"));

        // Вторая строка кнопок
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/motivate"));
        row2.add(new KeyboardButton("/discord"));
        row2.add(new KeyboardButton("/dota2"));

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();



            // Обработка команды /motivate
            if (messageText.equals("/motivate")) {
                try {
                    execute(Challenge.getMotivation(update)); // Challenge возвращает SendPhoto
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return; // чтобы бот не выполнял дальнейшую обработку
            }




            if (messageText.equals("/stat")) {
                try {
                    sendMessage(chatId, Commands.StatCommand.getSmartResponse(users, "" + chatId));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            // Обработка остальных команд
            Commands.Command cmd = commands.get(messageText);
            String response = (cmd != null)
                    ? cmd.getResponse()
                    : users.Produce("" + chatId, messageText, String.valueOf(Instant.ofEpochSecond(update.getMessage().getDate())));

            sendMessage(chatId, response);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("Markdown"); // или null, если без Markdown
        message.setReplyMarkup(getMainKeyboard()); // <-- подключаем клавиатуру

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "Cheburashka";
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
