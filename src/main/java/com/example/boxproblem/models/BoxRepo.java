package com.example.boxproblem.models;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxRepo {

    private static final List<Box> BOXES = List.of(
            new Box(1, 4, 5),
            new Box(2, 8, 12),
            new Box(3, 12, 20)
    );

    private static final List<Article> ITEMS = List.of(
            new Article(1, 1),
            new Article(2, 2),
            new Article(3, 4),
            new Article(4, 6),
            new Article(5, 8),
            new Article(6, 9),
            new Article(7, 12),
            new Article(8, 5),
            new Article(9, 9)
    );

    public List<Box> getSortedBoxes() {
        return BOXES;
    }

    // Get item by ID
    public Article getArticleById(int id) {
        return ITEMS.stream()
                .filter(article -> article.id() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No article with ID: " + id));
    }
}
