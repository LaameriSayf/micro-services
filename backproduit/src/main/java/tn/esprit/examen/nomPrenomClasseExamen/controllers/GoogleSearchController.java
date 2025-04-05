package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.nomPrenomClasseExamen.services.GoogleSearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class GoogleSearchController {

    @Autowired
    private GoogleSearchService googleSearchService;

    @GetMapping
    public List<Map<String, String>> rechercher(@RequestParam String query) {
        return googleSearchService.rechercher(query);
    }
}
