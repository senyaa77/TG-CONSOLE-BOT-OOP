package org.example.commands;

public class HelpCommand implements Command {

    @Override
    public String getResponse() {
        return """
                📘 *Справка по командам:*
                
                /start — приветствие  
                /hello — поздороваться  
                /goodbye — попрощаться  
                /discord — статус серверов Discord
                /dota2 — статус серверов Dota 2
                /brawlstars — статус серверов Brawl Stars
                /help — показать это меню 
                /stat — рекомендации на основе твоих заметок за день
                
                В будущем появятся команды:
                • /valorant  
                • /fortnite  
                
                """;
    }
}
