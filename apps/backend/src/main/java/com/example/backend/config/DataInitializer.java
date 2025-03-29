package com.example.backend.config;

import com.example.backend.model.Example;
import com.example.backend.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ExampleRepository exampleRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            List<String> categories = Arrays.asList(
                    "Technology", "Science", "Art", "Business", "Education",
                    "Health", "Sports", "Entertainment", "Food", "Travel");

            List<String> adjectives = Arrays.asList(
                    "Amazing", "Incredible", "Fascinating", "Remarkable", "Extraordinary",
                    "Innovative", "Creative", "Dynamic", "Efficient", "Reliable");

            List<String> nouns = Arrays.asList(
                    "Project", "Solution", "System", "Platform", "Application",
                    "Framework", "Tool", "Service", "Product", "Resource");

            for (int i = 1; i <= 50; i++) {
                String category = categories.get((i - 1) % categories.size());
                String adjective = adjectives.get((i - 1) % adjectives.size());
                String noun = nouns.get((i - 1) % nouns.size());

                Example example = new Example();
                example.setName(String.format("%s %s %s %d", adjective, category, noun, i));
                example.setDescription(String.format(
                        "This is a %s %s %s that demonstrates various features and capabilities. "
                                +
                                "It's part of our comprehensive collection of examples showcasing different aspects of %s.",
                        adjective.toLowerCase(), category.toLowerCase(), noun.toLowerCase(),
                        category.toLowerCase()));
                exampleRepository.save(example);
            }

            System.out.println("Successfully initialized 50 example entries!");
        };
    }
}