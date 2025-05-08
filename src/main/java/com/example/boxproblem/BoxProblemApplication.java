package com.example.boxproblem;

import com.example.boxproblem.models.Article;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

@SpringBootApplication
public class BoxProblemApplication implements CommandLineRunner {

    private final ArticleParsingService articleParsingService;
    private final BoxFinderService boxFinderService;

    public BoxProblemApplication(ArticleParsingService articleParsingService, BoxFinderService boxFinderService) {
        this.articleParsingService = articleParsingService;
        this.boxFinderService = boxFinderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BoxProblemApplication.class, args);
    }


    @Override
    public void run(String... args) {
        List<Article> articles = getUserInput();
        boxFinderService.findSmallestBox(articles)
                .ifPresentOrElse(
                        boxId -> out.println("Minsta kartong: " + boxId),
                        () -> out.println("Upphämtning krävs")
                );
    }

    private List<Article> getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            out.println("*** Kartongproblemet ***");
            out.println("Mata in antal och artikelnummer (t.ex. '7:6,4:2' för 7 st artikel 6 och 4 st artikel 2).");
            out.println("'exit' för att avsluta.");

            out.print("> ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                System.exit(0);
            }

            return articleParsingService.parseInput(input);
        }
    }
}
