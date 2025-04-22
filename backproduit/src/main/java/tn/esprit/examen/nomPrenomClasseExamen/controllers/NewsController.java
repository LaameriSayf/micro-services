package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Article;
import tn.esprit.examen.nomPrenomClasseExamen.services.NewsApiService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsApiService newsApiService;

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Article>> getNewsByCategory(@PathVariable String category) {
        List<Article> articles = newsApiService.getNewsByCategory(category);
        return ResponseEntity.ok(articles);
    }
}
