package com.sismics.music.core.service.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

// public class SpotifyService {
//     private SpotifyApiRequestStrategy apiRequestStrategy;

//     public SpotifyService(SpotifyApiRequestStrategy apiRequestStrategy) {
//         this.apiRequestStrategy = apiRequestStrategy;
//     }

//     public String executeApiRequest(String param) throws IOException {
//         return apiRequestStrategy.executeApiRequest(param);
//     }
// }

public interface SpotifyApiRequestStrategy {
    String executeApiRequest(String param) throws IOException;
}





       

// package com.sismics.music.core.service.spotify;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.MalformedURLException;
// import java.net.ProtocolException;
// import java.net.URL;
// import java.net.URLEncoder;
// import java.util.Base64;

// import org.json.JSONArray;
// import org.json.JSONObject;


// public class SpotifyService {

//     public static String getAccessToken() throws IOException {
//         String clientId="8a791cca4209480d9039f07481c0ebd2";
//         String clientSecret="6365aefe52954167af5adf10c1da6de7";

//         //Set up the Spotify API token endpoint
//         String tokenEndpoint = "https://accounts.spotify.com/api/token";

//         //Encode the client ID and client secret
//         String encodedClientCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

//         //Set up the grant type
//         String grantType = "client_credentials";


//         // Construct the token endpoint URL
//         URL tokenUrl = new URL(tokenEndpoint);
//         HttpURLConnection tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
//         tokenConnection.setRequestMethod("POST");
//         tokenConnection.setRequestProperty("Authorization", "Basic " + encodedClientCredentials);
//         tokenConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//         tokenConnection.setDoOutput(true);

//         // Encode the grant type
//         String encodedGrantType = URLEncoder.encode(grantType, "UTF-8");

//         // Construct the request body
//         String requestBody = "grant_type=" + encodedGrantType;

//         // Write the request body to the connection output stream
//         tokenConnection.getOutputStream().write(requestBody.getBytes());

//         // Read the response from the API
//         BufferedReader tokenReader = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
//         String tokenInputLine;
//         StringBuilder tokenResponse = new StringBuilder();
//         while ((tokenInputLine = tokenReader.readLine()) != null) {
//             tokenResponse.append(tokenInputLine);
//         }
//         tokenReader.close();
    
//         // Parse the response JSON to obtain the access token
//         String token = tokenResponse.toString();
//         JSONObject json = new JSONObject(token);
//         String accessToken = json.getString("access_token");
        
//         return accessToken;

//     }

//     public static String search(String query) throws IOException {
//         String AccessToken = getAccessToken();

//         // Set up the Spotify API search endpoint
//         String searchEndpoint = "https://api.spotify.com/v1/search?type=track&q=";

//         // Encode the query string
//         String encodedQuery = URLEncoder.encode(query, "UTF-8");

//         // Construct the final URL
//         URL searchUrl = new URL(searchEndpoint + encodedQuery);
//         HttpURLConnection searchConnection = (HttpURLConnection) searchUrl.openConnection();
//         searchConnection.setRequestMethod("GET");
//         searchConnection.setRequestProperty("Authorization", "Bearer " + AccessToken);

//         // Read the response from the API
//         BufferedReader searchReader = new BufferedReader(new InputStreamReader(searchConnection.getInputStream()));
//         String searchInputLine;
//         StringBuilder searchResponse = new StringBuilder();
//         while ((searchInputLine = searchReader.readLine()) != null) {
//             searchResponse.append(searchInputLine);
//         }
//         searchReader.close();

//         String res = searchResponse.toString();
        
//         return res;
        
//     }

//     public static String recommend(String seed_tracks) throws IOException {
//         String AccessToken = getAccessToken();
//         String recommendationsEndpoint = "https://api.spotify.com/v1/recommendations";

//         // Construct the final URL
//         URL recommendationsUrl = new URL(recommendationsEndpoint + "?seed_tracks=" + seed_tracks);
//         HttpURLConnection recommendationsConnection = (HttpURLConnection) recommendationsUrl.openConnection();
//         recommendationsConnection.setRequestMethod("GET");
//         recommendationsConnection.setRequestProperty("Authorization", "Bearer " + AccessToken);

//         // Read the response from the API
//         BufferedReader recommendationsReader = new BufferedReader(new InputStreamReader(recommendationsConnection.getInputStream()));
//         String recommendationsInputLine;
//         StringBuilder recommendationsResponse = new StringBuilder();
//         while ((recommendationsInputLine = recommendationsReader.readLine()) != null) {
//             recommendationsResponse.append(recommendationsInputLine);
//         }
//         recommendationsReader.close();

//         String res = recommendationsResponse.toString();

//         return res;
        
//     }
// }

