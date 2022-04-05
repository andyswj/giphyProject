package edu.nus.iss.databaseproject2.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class GiphyService {
    
    private static final String URL_GIPHY = "https://api.giphy.com/v1/gifs/search";

    private static final String API = "XIkyPDiOBh4LyGrx9asUB2BrmIU11z6i";
    
    //GIPHY_API_KEY
    // @Value("${giphy.api.key}")

    public List<String> getResult(String q) {
        return getResult(q, 10, "pg");
    }

    public List<String> getResult(String q, Integer limit) {
        return getResult(q, limit, "pg");
    }

    public List<String> getResult(String q, String rating) {
        return getResult(q, 10, rating);
    }
    
    public List<String> getResult(String q, Integer limit, String rating) {

        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder
            .fromUriString(URL_GIPHY)
            .queryParam("api_key", API)
            .queryParam("q", q)
            .queryParam("limit", limit)
            .queryParam("offset", 0)
            .queryParam("rating", rating)
            .queryParam("lang", "en")
            .toUriString();
        
        // System.out.println("URL: " + url);

        RequestEntity<Void> req = RequestEntity.get(url).build();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        if(resp.getStatusCodeValue() > 400) {
            return null;
        }

        List<String> result = getJson(resp);
        return result;
    }

    public List<String> getJson(ResponseEntity<String> resp) {

        List<String> result = new LinkedList<>();

        // JsonReader reader = Json.createReader(new StringReader(resp.getBody()));

        try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject data = r.readObject();

            JsonArray info = data.getJsonArray("data");

            info.stream()
                .map(v -> (JsonObject) v)
                .forEach(o -> {
                    JsonObject images = o.getJsonObject("images");
                    JsonObject fixedWidth = images.getJsonObject("fixed_width");
                    String link = fixedWidth.getString("url");
                    // System.out.println("url: " + link);
                    result.add(link);
                });

        } catch(Exception e) {
            System.out.println(">>>>>> Error: " + e.getMessage());
        }

        return result;
    }
}
