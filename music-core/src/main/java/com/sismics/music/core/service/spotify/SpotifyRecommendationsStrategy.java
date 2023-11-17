package com.sismics.music.core.service.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyRecommendationsStrategy implements SpotifyApiRequestStrategy {
    private final String recommendationsEndpoint = "https://api.spotify.com/v1/recommendations";

    public String executeApiRequest(String seed_tracks) throws IOException {
        String AccessToken = new SpotifyAccessTokenStrategy().executeApiRequest("");
        URL recommendationsUrl = new URL(recommendationsEndpoint + "?seed_tracks=" + seed_tracks);
        HttpURLConnection recommendationsConnection = (HttpURLConnection) recommendationsUrl.openConnection();
        recommendationsConnection.setRequestMethod("GET");
        recommendationsConnection.setRequestProperty("Authorization", "Bearer " + AccessToken);

        BufferedReader recommendationsReader = new BufferedReader(new InputStreamReader(recommendationsConnection.getInputStream()));
        String recommendationsInputLine;
        StringBuilder recommendationsResponse = new StringBuilder();
        while ((recommendationsInputLine = recommendationsReader.readLine()) != null) {
            recommendationsResponse.append(recommendationsInputLine);
        }

        recommendationsReader.close();

        String res = recommendationsResponse.toString();
        return res;
    }
}