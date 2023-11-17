package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.ws.rs.core.Response;

public class LastFmServiceInvoker {
    private LastFmServiceCommand lastFmServiceCommand;
    
    public void setLastFmServiceCommand(LastFmServiceCommand lastFmServiceCommand) {
        this.lastFmServiceCommand = lastFmServiceCommand;
    }
    
    public Response invoke() throws IOException {
        return lastFmServiceCommand.execute();
    }
}
