package UrfuBot;


import java.io.IOException;

public class Commands {

    public interface Command {
        String getResponse();
    }


    public static class DiscordCommand implements Command {
        @Override
        public String getResponse() {
            return StatusService.getDiscordStatus();
        }
    }

    public static class GoodbyeCommand implements Command {
        @Override
        public String getResponse() {
            return "👋 До встречи!";
        }
    }

    public static class HelloCommand implements Command {
        @Override
        public String getResponse() {
            return "👋 Привет! Я игровой статус-бот.\nПопробуй /discord или /help.";
        }
    }

    public static class HelpCommand implements Command {
        @Override
        public String getResponse() {
            return """
                📘 *Справка по командам:*
               \s
                /start — приветствие \s
                /hello — поздороваться \s
                /goodbye — попрощаться \s
                /discord — статус серверов Discord
                /dota2 — статус серверов Dota 2
                /brawl — статус серверов Brawl Stars
                /help — показать это меню\s
                /stat — рекомендации на основе твоих заметок за день
               \s
                В будущем появятся команды:
                • /valorant \s
                • /fortnite \s
               \s
               \s""";
        }
    }

    public static class StartCommand implements Command {
        @Override
        public String getResponse() {
            return "🚀 Бот запущен! Используйте /help для просмотра команд.";
        }
    }

    public static class Dota2Command implements Command {

        @Override
        public String getResponse() {
            return StatusService.getDota2Status();
        }
    }

    public static class BrawlStarsCommand implements Command {

        @Override
        public String getResponse() {
            return StatusService.getBrawlStarsStatus();
        }
    }

    public static class StatCommand {

        public static final String prompt = """
                это логи для человека, нужно дать ему рекомендации по цифровому детоксу\
                (например меньше смотрть ютуб, если он него смотрел больше часа в день
                
                """;
        public static OpenRouter openRouter = new OpenRouter();

        public static String getSmartResponse(Users users, String chatId) throws IOException, InterruptedException {
            return openRouter.sendRequest(prompt + users.Get(chatId));
        }
    }


}
