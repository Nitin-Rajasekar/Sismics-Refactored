package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sismics.music.core.service.lastfm.LastFmService;

public class LastFmRecommendationsCommand implements LastFmServiceCommand {
    private String seed_mbids;
    private LastFmService lastFmService;
    
    public LastFmRecommendationsCommand(String seed_mbids, LastFmService lastFmService) {
        this.seed_mbids = seed_mbids;
        this.lastFmService = lastFmService;
    }

    @Override
    public Response execute() throws IOException {
        String searchResponse = LastFmService.recommend(seed_mbids);
        
        System.out.println("searchResponse: " + searchResponse);

        JSONObject trackjson = new JSONObject(searchResponse);

        JSONArray tracks = trackjson.getJSONObject("similartracks").getJSONArray("track");

        JsonObjectBuilder recommendJson = Json.createObjectBuilder();
        JsonArrayBuilder recommendArray = Json.createArrayBuilder();

        for(Object each:tracks)
        {
            JSONObject track = (JSONObject) each;
            recommendArray.add(Json.createObjectBuilder().add("name",track.getString("name")));
        }

        recommendJson.add("tracks",recommendArray);
        // return renderJson(recommendJson);
        return Response.ok(recommendJson.build()).build();
    }
}