package org.example.commands;

import org.example.services.BrawlStarsStatusService;

public class Dota2Command implements Command{

    @Override
    public String getResponse() {
        return BrawlStarsStatusService.getBrawlStarsStatus();
    }
}
