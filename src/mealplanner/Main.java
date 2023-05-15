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

    public static void main(String[] args) {

        // Creates a scanner object
        Scanner scanner = new Scanner(System.in);
        ArrayList<Meal> listOfMeals = new ArrayList<>();

        // Main engine
        while (true) {

            // Sets scanner object to the
            ControllerInterface.setScanner(scanner);

            // Show Available action commands.
            ControllerInterface.showAvailableCommands();

            String command = ControllerInterface.getActionCommand();

            switch (command) {
                case "add":
                    Meal meal = ControllerInterface.addMealCommand();
                    if (meal != null) {
                        listOfMeals.add(meal);
                        System.out.println("The meal has been added!");
                    }
                    break;
                case "show":
                    ControllerInterface.showMeals(listOfMeals);
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    break;

            }
        } // End of loop

    }
}