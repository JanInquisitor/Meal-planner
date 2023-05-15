package mealplanner;

import java.util.List;
import java.util.Scanner;

// This is a mess...
public class ControllerInterface {

    public static Scanner scanner;

    public static void showAvailableCommands() {
        System.out.println("What would you like to do (add, show, exit)?");
    }

    public static String getActionCommand() {
        return scanner.nextLine();
    }

    public static Meal addMealCommand() {
        // Prints questions and receive inputs for lunch type, name and ingredients.
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        String category;
        String name;
        String ingredientsString;

        while (true) {
            category = scanner.nextLine();

            if (category.isEmpty()) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                continue;
            }

            if (!category.equals("breakfast") && !category.equals("lunch") && !category.equals("dinner")) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                continue;
            }

            // Use a label to jump back to this point in case of invalid name input
            label1:
            while (true) {
                System.out.println("Input the meal's name:");
                name = scanner.nextLine();

                if (!name.matches("^[a-zA-Z]+$")) {
                    System.out.println("Wrong format. Use letters only!");
                    continue label1; // continue from label1 instead of the beginning of the loop
                }

//                scanner.nextLine(); // Consume the newline character

                label2:
                while (true) {
                    System.out.println("Input the ingredients:");
                    ingredientsString = scanner.nextLine();

                    if (ingredientsString.isEmpty() || !ingredientsString.matches("[a-zA-Z ,]+")) {
                        System.out.println("Wrong format. Use letters only!");
//                        return null
                        continue label2;
                    }

                    String[] ingredients = ingredientsString.split(", ");

                    for (String ingredient : ingredients) {
                        if (!ingredient.matches("[a-zA-Z ,]+")) {
                            continue label2;
                        }
                    }
                    // Create meal object
                    return new Meal(category, name, ingredients);
                }
            }
        }
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

    public static void setScanner(Scanner scannerObject) {
        scanner = scannerObject;
    }
}
