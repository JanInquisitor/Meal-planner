package mealplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Meal {
    private final String name;
    private final String category;
    private final String[] ingredients;

    public Meal(String category, String name, String[] ingredients) {
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void printIngredients() {
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Meal> listOfMeals = new ArrayList<>();

        // Main engine
        while (true) {

            // Show Available action commands.
            Utils.showAvailableCommands();

            // Get action command from the user.
            String command = scanner.nextLine();

            // Execute the specified command.
            switch (command) {
                case "add" -> {
                    Meal meal = createMeal();
                    listOfMeals.add(meal);
                    System.out.println("The meal has been added!");
                }
                case "show" -> Utils.showMeals(listOfMeals);
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> {
                }
            }

        } // End of loop

    }

    public static Meal createMeal() {
        // Prints questions and receive inputs for lunch type, name and ingredients.
        String category = getCategoryInput();
        String name = getNameInput();
        String[] ingredients = getIngredients();

        return new Meal(category, name, ingredients);
    }

    private static String getCategoryInput() {
        String category;
        while (true) {
            System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            category = scanner.nextLine();
            if (category.isEmpty()) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.\n");
                continue;
            }
            if (!category.equals("breakfast") && !category.equals("lunch") && !category.equals("dinner")) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.\n");
            } else {
                break;
            }
        }
        return category;
    }

    private static String getNameInput() {
        String name;
        while (true) {
            System.out.println("Input the meal's name:");
            name = scanner.nextLine();
            if (!containsOnlyLetters(name)) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                break;
            }
        }
        return name;
    }

    private static String[] getIngredients() {
        String ingredientsString;
        String[] ingredients;
        do {
            System.out.println("Input the ingredients:");
            ingredientsString = scanner.nextLine();
            ingredients = ingredientsString.split(",");
        } while (!validateIngredients(ingredients));
        return ingredients;
    }

    private static boolean validateIngredients(String[] ingredients) {
        for (String ingredient : ingredients) {
            if (ingredient.isBlank()) {
                System.out.println("Wrong format. Use letters only!");
                return false;
            }
            ingredient = ingredient.trim();
            if (!containsOnlyLetters(ingredient)) {
                System.out.println("Wrong format. Use letters only!");
                return false;
            }
        }
        return true;
    }

    private static boolean containsOnlyLetters(String str) {
        return str.matches("[a-zA-Z]+( [a-zA-Z]+)?");
    }
}