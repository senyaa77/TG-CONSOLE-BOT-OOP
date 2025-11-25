package org.example.commands;

import org.example.services.BrawlStarsStatusService;

public class BrawlStarsCommand implements Command {

    @Override
    public String getResponse() {
        return BrawlStarsStatusService.getBrawlStarsStatus();
    }
}
