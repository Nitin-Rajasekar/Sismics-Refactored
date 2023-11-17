package com.sismics.music.rest.resource.command;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sismics.music.core.service.spotify.SpotifyApiRequestStrategy;

public class SpotifyRecommendationsCommand implements SpotifyServiceCommand {
    private String seed_tracks;
    private SpotifyApiRequestStrategy spotifyService;
    
    public SpotifyRecommendationsCommand(String seed_tracks, SpotifyApiRequestStrategy spotifyService) {
        this.seed_tracks = seed_tracks;
        this.spotifyService = spotifyService;
    }

    @Override
    public Response execute() throws IOException {
        // String searchResponse = spotifyService.search(song);
        
        // SpotifyApiRequestStrategy spotifyService = new SpotifySearchStrategy();

        String response = spotifyService.executeApiRequest(seed_tracks);

        JSONObject resJson = new JSONObject(response);

        JSONArray tracks = resJson.getJSONArray("tracks");

        JsonObjectBuilder recommendedJson = Json.createObjectBuilder();
        JsonArrayBuilder recommendedNames = Json.createArrayBuilder();

        for(Object each:tracks)
        {
            JSONObject track = (JSONObject) each;
            recommendedNames.add(Json.createObjectBuilder().add("name",track.getString("name")).add("id",track.getString("id")));
        }

        recommendedJson.add("tracks",recommendedNames);

        return Response.ok(recommendedJson.build()).build();
    }
    
}
