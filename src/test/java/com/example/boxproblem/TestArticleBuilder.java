package com.example.boxproblem;

import com.example.boxproblem.models.Article;

import java.util.ArrayList;
import java.util.List;

public class TestArticleBuilder {
    private final List<Article> articles = new ArrayList<>();

    public TestArticleBuilder withArticles(int count, int height) {
        for (int i = 0; i < count; i++) {
            articles.add(new Article(i, height));
        }
        return this;
    }

    public List<Article> build() {
        return articles;
    }
}
