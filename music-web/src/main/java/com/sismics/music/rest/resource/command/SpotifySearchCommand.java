package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sismics.music.core.service.spotify.SpotifyApiRequestStrategy;


import com.sismics.music.core.service.spotify.SpotifySearchStrategy;

public class SpotifySearchCommand implements SpotifyServiceCommand {
    private String song;
    private SpotifyApiRequestStrategy spotifyService;
    
    public SpotifySearchCommand(String song, SpotifyApiRequestStrategy spotifyService) {
        this.song = song;
        this.spotifyService = spotifyService;
    }

    @Override
    public Response execute() throws IOException {
        // String searchResponse = spotifyService.search(song);
        
        // SpotifyApiRequestStrategy spotifyService = new SpotifySearchStrategy();

        String searchResponse = spotifyService.executeApiRequest(song);

        JSONObject trackjson = new JSONObject(searchResponse);

        JSONArray tracks = trackjson.getJSONObject("tracks").getJSONArray("items");

        JsonObjectBuilder searchJson = Json.createObjectBuilder();
        JsonArrayBuilder searchArray = Json.createArrayBuilder();

        for(Object each:tracks)
        {
            JSONObject track = (JSONObject) each;
            searchArray.add(Json.createObjectBuilder().add("id",track.getString("id")).add("name",track.getString("name")));
            
        }

        searchJson.add("tracks",searchArray);

        return Response.ok(searchJson.build()).build();
    }
}
