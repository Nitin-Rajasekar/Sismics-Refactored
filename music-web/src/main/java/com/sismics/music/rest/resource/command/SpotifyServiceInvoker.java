package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.ws.rs.core.Response;

public class SpotifyServiceInvoker {
    private SpotifyServiceCommand spotifyServiceCommand;

    public void setSpotifyServiceCommand(SpotifyServiceCommand spotifyServiceCommand) {
        this.spotifyServiceCommand = spotifyServiceCommand;
    }

    public Response invoke() throws IOException {
        return spotifyServiceCommand.execute();
    }
}
