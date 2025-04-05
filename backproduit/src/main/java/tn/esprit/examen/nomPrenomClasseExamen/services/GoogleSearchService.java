package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GoogleSearchService {

    private static final String API_KEY = "AIzaSyDWjwUZ5wfXjzKT-BeelM8_Vpqw1jM5scg";
    private static final String CSE_ID = "122c8d3e3aee9488f";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, String>> rechercher(String query) {
        String url = "https://www.googleapis.com/customsearch/v1"
                + "?key=" + API_KEY
                + "&cx=" + CSE_ID
                + "&q=" + query;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> json = response.getBody();

        List<Map<String, Object>> items = (List<Map<String, Object>>) json.get("items");

        if (items == null) return Collections.emptyList();

        List<Map<String, String>> results = new ArrayList<>();
        for (Map<String, Object> item : items) {
            Map<String, String> entry = new HashMap<>();
            entry.put("titre", (String) item.get("title"));
            entry.put("lien", (String) item.get("link"));
            entry.put("description", (String) item.get("snippet"));

            // 🔍 image via pagemap -> cse_image
            Map<String, Object> pagemap = (Map<String, Object>) item.get("pagemap");
            if (pagemap != null && pagemap.containsKey("cse_image")) {
                List<Map<String, Object>> images = (List<Map<String, Object>>) pagemap.get("cse_image");
                if (!images.isEmpty() && images.get(0).containsKey("src")) {
                    entry.put("image", (String) images.get(0).get("src"));
                }
            }

            results.add(entry);
        }

        return results;
    }
}