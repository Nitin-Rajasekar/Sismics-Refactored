package com.sismics.music.core.service.spotify;

// import 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SpotifySearchStrategy implements SpotifyApiRequestStrategy {
    private final String searchEndpoint = "https://api.spotify.com/v1/search?type=track&q=";

    public String executeApiRequest(String query) throws IOException {
        String AccessToken = new SpotifyAccessTokenStrategy().executeApiRequest("");
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        URL searchUrl = new URL(searchEndpoint + encodedQuery);
        HttpURLConnection searchConnection = (HttpURLConnection) searchUrl.openConnection();
        searchConnection.setRequestMethod("GET");
        searchConnection.setRequestProperty("Authorization", "Bearer " + AccessToken);
        BufferedReader searchReader = new BufferedReader(new InputStreamReader(searchConnection.getInputStream()));
        String searchInputLine;
        StringBuilder searchResponse = new StringBuilder();
        while ((searchInputLine = searchReader.readLine()) != null) {
            searchResponse.append(searchInputLine);
        }
        searchReader.close();
        String res = searchResponse.toString();
        return res;
    }
}
