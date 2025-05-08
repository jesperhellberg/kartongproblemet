package com.example.boxproblem;

import com.example.boxproblem.models.Article;
import com.example.boxproblem.models.BoxRepo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ArticleParsingService {

    private final BoxRepo boxRepo;

    public ArticleParsingService(BoxRepo boxRepo) {
        this.boxRepo = boxRepo;
    }

    /**
     * Parse articles from user input.
     *
     * @param input user input string.
     * @return A list containing every article ordered by descending height.
     */
    public List<Article> parseInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Inga artiklar angavs");
        }

        return Arrays.stream(input.trim().split(","))
                .flatMap(this::parsePairs)
                .sorted(Comparator.comparingInt(Article::height).reversed())
                .toList();
    }

    private Stream<Article> parsePairs(String pair) {
        String[] parts = pair.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Felaktigt format. Ange 'antal:artikelnummer'");
        }

        int quantity = Integer.parseInt(parts[0]);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Antal måste vara ett positivt heltal");
        }

        int article = Integer.parseInt(parts[1]);
        Article articleById = boxRepo.getArticleById(article);

        return Stream.generate(() -> articleById).limit(quantity);
    }
}
