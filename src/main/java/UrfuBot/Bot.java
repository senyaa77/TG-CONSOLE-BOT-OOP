package UrfuBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

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
