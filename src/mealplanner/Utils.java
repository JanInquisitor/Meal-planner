package mealplanner;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

// This is a mess...
public class Utils {
    private static final int MIN_ID = 1;
    private static final int MAX_ID = 1000;
    private static final Random random = new Random();

    public static int generateRandomId() {
        return random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
    }

    public static void showAvailableCommands() {
        System.out.println("What would you like to do (add, show, exit)?");
    }

    // @TODO: Make this method generic, and I mean make an alternative one that can print any type of object, maybe or maybe not I don't know.
    public static void showMeals(List<Meal> meals) {
        if (meals.size() == 0) {
            System.out.println("No meals found.");
            return;
        }
        for (Meal meal : meals) {
//            System.out.println("Category: " + meal.getCategory());
            System.out.println("Name: " + meal.getName());
            System.out.println("Ingredients: ");
            meal.printIngredients();
        }
    }

    public static void showMealsByCategory(List<Meal> meals, String category) {
        if (meals.size() == 0) {
            System.out.println("No meals found.");
            return;
        }

        System.out.println("Category: " + category);
        System.out.println("");

        for (Meal meal : meals) {
//            System.out.println("Category: " + meal.getCategory());
            System.out.println("Name: " + meal.getName());
            System.out.println("Ingredients: ");
            meal.printIngredients();
        }
    }
}
