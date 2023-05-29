package mealplanner;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class Meal {
    private final int id;

    private final String name;
    private final String category;
    private final IngredientList ingredients;

    public Meal(String category, String name, List<String> ingredientsList) {
        this.id = Utils.generateRandomId();
        this.category = category;
        this.name = name;
        this.ingredients = new IngredientList(ingredientsList);
    }

    public Meal(String category, String name, String[] ingredientsList) {
        this.id = Utils.generateRandomId();
        this.category = category;
        this.name = name;
        this.ingredients = new IngredientList(ingredientsList);
    }

    public Meal(String category, String name, IngredientList ingredientsList) {
        this.id = Utils.generateRandomId();
        this.category = category;
        this.name = name;
        this.ingredients = ingredientsList;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public IngredientList getIngredients() {
        return this.ingredients;
    }

    public static String[] parseIngredientsString(String str) {
        return str.split(",\\s*");
    }

    public void printIngredients() {
        for (String ingredient : ingredients.list.toArray(new String[0])) {
            System.out.println(ingredient);
        }
        System.out.println("");
    }
}


public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static DatabaseConnection connection = DatabaseConnection.createDatabaseConnection();

    public static void main(String[] args) {
        try {
            // Main loop of the program
            mainEngine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mainEngine() throws SQLException {
        connection.createTables();

        // Main engine
        while (true) {

            // Show Available action commands.
            Utils.showAvailableCommands();

            // Get action command from the user.
            String command = scanner.nextLine();

            // Execute the specified command.
            switch (command) {

                case "add" -> {

                    // Creates a meal object.
                    Meal meal = createMeal();

                    // Add created meal to the database
                    connection.storeMeal(meal.getCategory(), meal.getName(), meal.getId());

                    // Reads the created meal from the database and get the id
                    int mealId = connection.getMealIdByName(meal.getName());

                    IngredientList ingredients = meal.getIngredients();

                    // Add the ingredients to the database.
                    connection.storeIngredients(ingredients.getIngredientsString(), ingredients.getId(), mealId);

                    // Confirms the user that the creation was successful.
                    System.out.println("The meal has been added!");

                }
                case "show" -> {
                    showCommand();
                }
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> {
                }
            }

        } // End of loop
    }

    private static void showCommand() throws SQLException {
        do {
            System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
            String category = scanner.nextLine();
            if (category.isEmpty() || (category.equals("breakfast") || category.equals("lunch") || category.equals("dinner"))) {
                List<Meal> meals = connection.getMealsByCategory(category);
                Utils.showMealsByCategory(meals, category);
                break;
            } else {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        } while (true);
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
        do {
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
        } while (true);
        return category;
    }

    private static String getNameInput() {
        String name;
        do {
            System.out.println("Input the meal's name:");
            name = scanner.nextLine();
            if (!containsOnlyLetters(name)) {
                System.out.println("Wrong format. Use letters only!");
            } else {
                break;
            }
        } while (true);
        return name;
    }

    private static String[] getIngredients() {
        String ingredientsString;
        String[] ingredientsArr;
        do {
            System.out.println("Input the ingredients:");
            ingredientsString = scanner.nextLine();
            ingredientsArr = ingredientsString.split(", ");
        } while (!validateIngredients(ingredientsArr));
        return ingredientsArr;
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