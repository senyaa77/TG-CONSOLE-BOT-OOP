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
                /help — показать это меню 
                
                В будущем появятся команды:
                • /valorant  
                • /fortnite  
                
                """;
    }
}
