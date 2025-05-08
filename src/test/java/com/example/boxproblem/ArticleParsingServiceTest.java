package com.example.boxproblem;

import com.example.boxproblem.models.Article;
import com.example.boxproblem.models.BoxRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleParsingServiceTest {
    @Mock
    private BoxRepo boxRepo;

    @InjectMocks
    private ArticleParsingService articleParsingService;


    @ParameterizedTest()
    @ValueSource(strings = {"", "12", "12,3", "1:2:3", ",2", "0:1", "-1:1"})
    void wrongInputThrows(String input) {
        assertThrows(IllegalArgumentException.class, () -> articleParsingService.parseInput(input));
    }

    @Test
    void shouldReturnTwoIdenticalArticles() {
        when(boxRepo.getArticleById(1)).thenReturn(new Article(1, 1));
        List<Article> articles = articleParsingService.parseInput("2:1");
        assertEquals(2, articles.size());
        assertEquals(1, articles.get(0).id());
        assertEquals(1, articles.get(1).id());
    }

    @Test
    void shouldReturnListSortedByHeight() {
        when(boxRepo.getArticleById(1)).thenReturn(new Article(1, 2));
        when(boxRepo.getArticleById(2)).thenReturn(new Article(2, 3));
        when(boxRepo.getArticleById(3)).thenReturn(new Article(3, 1));
        List<Article> articles = articleParsingService.parseInput("1:1,1:2,1:3");
        assertEquals(3, articles.size());
        assertEquals(2, articles.get(0).id());
        assertEquals(1, articles.get(1).id());
        assertEquals(3, articles.get(2).id());
    }
}
