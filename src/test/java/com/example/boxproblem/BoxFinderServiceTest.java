package com.example.boxproblem;

import com.example.boxproblem.models.Article;
import com.example.boxproblem.models.Box;
import com.example.boxproblem.models.BoxRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoxFinderServiceTest {
    @Mock
    private BoxRepo boxRepo;

    @InjectMocks
    private BoxFinderService boxFinderService;

    @Test
    void shouldReturnBoxIdWhenOneArticleFits() {
        when(boxRepo.getSortedBoxes()).thenReturn(List.of(new Box(1, 4, 5)));
        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(List.of(new Article(1, 1)));
        assertTrue(smallestBox.isPresent());
        assertEquals(1, smallestBox.get().intValue());
    }

    @Test
    void shouldReturnEmptyWhenNoArticleFits() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12))
        );
        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(List.of(new Article(2, 13)));
        assertFalse(smallestBox.isPresent());
    }

    // *** Example inputs
    @Test
    void shouldReturnBoxId1WhenMultipleArticlesFit() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12),
                        new Box(3, 12, 20)));

        List<Article> articles = new TestArticleBuilder()
                .withArticles(3, 4)
                .withArticles(1, 2)
                .withArticles(1, 1)
                .build();

        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(articles);
        assertTrue(smallestBox.isPresent());
        assertEquals(1, smallestBox.get().intValue());
    }

    @Test
    void shouldReturnBoxId1WhenMultipleArticlesFit2() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12),
                        new Box(3, 12, 20)));

        List<Article> articles = new TestArticleBuilder()
                .withArticles(4, 5)
                .build();

        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(articles);
        assertTrue(smallestBox.isPresent());
        assertEquals(1, smallestBox.get().intValue());
    }

    @Test
    void shouldReturnBoxId2WhenMultipleArticlesFit() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12),
                        new Box(3, 12, 20)));

        List<Article> articles = new TestArticleBuilder()
                .withArticles(6, 12)
                .withArticles(2, 6)
                .withArticles(4, 1)
                .build();

        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(articles);
        assertTrue(smallestBox.isPresent());
        assertEquals(2, smallestBox.get().intValue());
    }

    @Test
    void shouldReturnBoxId2WhenMultipleArticlesFit2() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12),
                        new Box(3, 12, 20)));

        List<Article> articles = new TestArticleBuilder()
                .withArticles(1, 8)
                .withArticles(3, 6)
                .build();

        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(articles);
        assertTrue(smallestBox.isPresent());
        assertEquals(2, smallestBox.get().intValue());
    }

    @Test
    void shouldReturnEmptyWhenArticlesDontFit() {
        when(boxRepo.getSortedBoxes()).thenReturn(
                List.of(new Box(1, 4, 5),
                        new Box(2, 8, 12),
                        new Box(3, 12, 20)));

        List<Article> articles = new TestArticleBuilder()
                .withArticles(12, 12)
                .withArticles(100, 1)
                .build();

        Optional<Integer> smallestBox = boxFinderService.findSmallestBox(articles);
        assertTrue(smallestBox.isEmpty());
    }
}
