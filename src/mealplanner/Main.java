package mealplanner;

import java.io.IOException;
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

    public Meal(int id, String category, String name, IngredientList ingredientsList) {
        this.id = id;
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
        return ingredients;
    }

    public static String[] parseIngredientsString(String str) {
        return str.split(",\\s*");
    }

    public void printIngredients() {
        for (String ingredient : ingredients.list.toArray(new String[0])) {
            System.out.println(ingredient);
        }
    }


    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static DatabaseConnection connection = DatabaseConnection.createDatabaseConnection();

    public static Plan plan;

    public static void main(String[] args) {
        try {
            connection.createTables();

            Plan previousPlan = null;

            try {
                previousPlan = connection.getPlans();
            } catch (SQLException e) {
                // ignore
            }

            if (previousPlan != null) {
                plan = previousPlan;
            }

            // Main loop of the program
            mainEngine();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mainEngine() throws SQLException, IOException {
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
                    connection.add(meal);

                    // Reads the created meal from the database and get the id
                    int mealId = connection.getMealIdByName(meal.getName()); // @TODO: I could just use the Meal object I just created for this step..lol

                    IngredientList ingredients = meal.getIngredients();

                    // Add the ingredients to the database.
                    connection.storeIngredients(ingredients, mealId);

                    // Confirms the user that the creation was successful.
                    System.out.println("The meal has been added!");

                }
                case "show" -> {
                    showCommand();
                }
                case "plan" -> {
                    planCommand();
                }
                case "save" -> {
                    saveCommand();
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

    private static void saveCommand() throws SQLException, IOException {
        if (isMealPlanReady()) {
            System.out.println("Input a filename:");
            String filename = scanner.nextLine();
            Save.saveShoppingList(filename, plan);
        } else {
            System.out.println("Unable to save. Plan your meals first.");
        }

    }

    private static void planCommand() throws SQLException {
        plan = Planning.createPlan();
        Planning.printWeekPlan(plan);
        connection.savePlan(plan);
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
            ingredientsArr = ingredientsString.split(",");
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
        return str.matches("[a-zA-Z]+( [a-zA-Z]+)*");
    }

    private static boolean isMealPlanReady() {
        return plan != null;
    }


}