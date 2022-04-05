package edu.nus.iss.databaseproject2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.nus.iss.databaseproject2.services.GiphyService;

@Controller
public class GiphyController {

    @Autowired
    private GiphyService giphySvc;

    @GetMapping(path="/search")
    public String search(@RequestParam MultiValueMap<String, String> query, Model model) {
        
        final String q = query.getFirst("q");
        final Integer limit = Integer.parseInt(query.getFirst("limit"));
        final String rating = query.getFirst("rating");

        // System.out.println(q + " " + limit + " " + rating);

        List<String> result = giphySvc.getResult(q, limit, rating);

        model.addAttribute("url", result);
        model.addAttribute("search", q);
        
        return "search";
    }
    
}
