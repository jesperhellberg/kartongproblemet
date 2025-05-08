package com.example.boxproblem;

import com.example.boxproblem.models.Article;
import com.example.boxproblem.models.Box;
import com.example.boxproblem.models.BoxRepo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BoxFinderService {
    private final BoxRepo boxRepo;

    public BoxFinderService(BoxRepo boxRepo) {
        this.boxRepo = boxRepo;
    }

    /**
     * Finds the smallest box that will fit all the articles.
     *
     * @param articles List of articles to fit. The list must be sorted by height in descending order for optimal packing.
     * @return an Optional containing the boxId of the smallest box found, or empty if no box will hold all articles.
     */
    public Optional<Integer> findSmallestBox(List<Article> articles) {
        List<Box> sortedBoxes = boxRepo.getSortedBoxes();

        for (Box box : sortedBoxes) {
            if (canFitInBox(box, articles)) {
                return Optional.of(box.id());
            }
        }
        return Optional.empty();
    }

    /**
     * Tries to fit all the articles in a given box. The box is filled a column at the time, with the biggest (highest)
     * fitting article available.
     *
     * @param box A box to fit the articles inside.
     * @param articles List of articles to fit. The list must be sorted by height in descending order for optimal packing.
     * @return true if all articles fit in the box, otherwise false.
     */
    private boolean canFitInBox(Box box, List<Article> articles) {

        int[] availableHeightPerColumn = new int[box.width()];
        Arrays.fill(availableHeightPerColumn, box.height());

        for (Article article : articles) {
            boolean articleFitted = false;

            for (int column = 0; column < box.width(); column++) {
                if (availableHeightPerColumn[column] >= article.height()) {
                    availableHeightPerColumn[column] -= article.height();
                    articleFitted = true;
                    break;
                }
            }

            if (!articleFitted) {
                return false;
            }
        }
        return true;
    }
}
