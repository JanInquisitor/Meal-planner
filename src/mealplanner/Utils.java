package mealplanner;

import java.util.List;
import java.util.Scanner;

// This is a mess...
public class Utils {

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
