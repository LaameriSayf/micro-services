package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NewsApiService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "4cc71b285d9742a482fc602634709684"; // Remplace par ta clé API
    private final String BASE_URL = "https://newsapi.org/v2/top-headlines";

    public List<Article> getNewsByCategory(String category) {
        String url = String.format("%s?category=%s&apiKey=%s", BASE_URL, category, API_KEY);

        // Faire la requête GET à NewsAPI
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> articles = (List<Map<String, Object>>) body.get("articles");

        // Convertir chaque article en un objet Article
        List<Article> articleList = new ArrayList<>();
        for (Map<String, Object> articleMap : articles) {
            articleList.add(new Article(
                    (String) articleMap.get("title"),
                    (String) articleMap.get("description"),
                    (String) articleMap.get("url"),
                    (String) articleMap.get("publishedAt")
            ));
        }

        return articleList;
    }
}
