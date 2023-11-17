package com.sismics.music.core.service.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import org.json.JSONObject;

// import com.sismics.music.core.service.spotify.SpotifyService;

public class SpotifyAccessTokenStrategy implements SpotifyApiRequestStrategy {
    private final String clientId = "8a791cca4209480d9039f07481c0ebd2";
    private final String clientSecret = "6365aefe52954167af5adf10c1da6de7";
    private final String tokenEndpoint = "https://accounts.spotify.com/api/token";
    
    public String executeApiRequest(String param) throws IOException {
        String encodedClientCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        String grantType = "client_credentials";
        URL tokenUrl = new URL(tokenEndpoint);
        HttpURLConnection tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
        tokenConnection.setRequestMethod("POST");
        tokenConnection.setRequestProperty("Authorization", "Basic " + encodedClientCredentials);
        tokenConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        tokenConnection.setDoOutput(true);
        String encodedGrantType = URLEncoder.encode(grantType, "UTF-8");
        String requestBody = "grant_type=" + encodedGrantType;
        tokenConnection.getOutputStream().write(requestBody.getBytes());
        BufferedReader tokenReader = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
        String tokenInputLine;
        StringBuilder tokenResponse = new StringBuilder();
        while ((tokenInputLine = tokenReader.readLine()) != null) {
            tokenResponse.append(tokenInputLine);
        }
        tokenReader.close();
        String token = tokenResponse.toString();
        JSONObject json = new JSONObject(token);
        String accessToken = json.getString("access_token");
        return accessToken;
    }
}

