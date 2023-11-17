package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.ws.rs.core.Response;


public interface LastFmServiceCommand {
    public Response execute() throws IOException;
}

