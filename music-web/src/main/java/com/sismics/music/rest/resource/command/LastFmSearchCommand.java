package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sismics.music.core.service.lastfm.LastFmService;

public class LastFmSearchCommand implements LastFmServiceCommand {
    private String song;
    private LastFmService lastFmService;
    
    public LastFmSearchCommand(String song, LastFmService lastFmService) {
        this.song = song;
        this.lastFmService = lastFmService;
    }

    @Override
    public Response execute() throws IOException {
        String searchResponse = lastFmService.search(song);
        
        // String searchResponse = LastFmService.search(song);

        JSONObject trackjson = new JSONObject(searchResponse);

        JSONArray tracks = trackjson.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");

        JsonObjectBuilder searchJson = Json.createObjectBuilder();
        JsonArrayBuilder searchArray = Json.createArrayBuilder();

        for(Object each:tracks)
        {
            JSONObject track = (JSONObject) each;
            searchArray.add(Json.createObjectBuilder().add("id",track.getString("mbid")).add("name",track.getString("name")).add("mbid",track.getString("mbid")));
            
        }

        searchJson.add("tracks",searchArray);
        // return renderJson(searchJson);

        return Response.ok(searchJson.build()).build();
    }
}
