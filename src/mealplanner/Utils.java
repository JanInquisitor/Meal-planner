package mealplanner;

import java.util.List;
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

    public static void showMeals(List<Meal> meals) {
        if (meals.size() == 0) {
            System.out.println("No meals saved. Add a meal first.");
        }

        for (Meal meal : meals) {
            System.out.println("");
            System.out.println("Category: " + meal.getCategory());
            System.out.println("Name: " + meal.getName());
            System.out.println("Ingredients: ");
            meal.printIngredients();
        }

    }
}
