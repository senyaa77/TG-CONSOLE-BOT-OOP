package org.example.commands;

import org.example.services.DiscordStatusService;

public class DiscordCommand implements Command {

    @Override
    public String getResponse() {
        return DiscordStatusService.getDiscordStatus();
    }
}
