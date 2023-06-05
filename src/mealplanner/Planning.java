package mealplanner;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

// This class will handle the planning functionality logic.
// Maybe I should make a class for the logic of creation of Meals that is in the main file and make it(like this file) have
// and handle its own DatabaseConnection object instead of doing it in the Main file.
public class Planning {

    public static DatabaseConnection connection = DatabaseConnection.createDatabaseConnection();

    public static Scanner scanner = new Scanner(System.in);
    static String[] daysOfTheWeek = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static Plan createPlan() throws SQLException {
        Day[] week = new Day[7];

        int day = 0;

        for (int i = 0; i < daysOfTheWeek.length; i++) {

            String nameOfDay = daysOfTheWeek[day];

            System.out.println(nameOfDay);

            Meal breakfast = getBreakfast(nameOfDay);

            Meal lunch = getLunch(nameOfDay);

            Meal dinner = getDinner(nameOfDay);

            week[day] = new Day(nameOfDay, breakfast, lunch, dinner);


            System.out.println("Yeah! We planned the meals for " + nameOfDay + ".");
            System.out.println("");

            day++;

        }

        return new Plan(week);
    }

    public static void savePlan(Plan plan) {

    }

    public static void printWeekPlan(Plan plan) {
        Day[] week = plan.getWeek();
        for (Day day : week) {
            day.printDay();
            System.out.println("");
        }
    }

    private static Meal getDinner(String nameOfDay) throws SQLException {
        List<Meal> dinnerList = connection.getMealsByCategoryOrdered("dinner");
        Utils.showMeals(dinnerList);
        Meal dinner;
        do {
            System.out.println("Choose the dinner for " + nameOfDay + " from the list above:");
            String input = scanner.nextLine();
            if (dinnerList.stream().anyMatch(meal -> input.equals(meal.getName()))) {
                int dinnerId = connection.getMealIdByName(input);
                dinner = connection.getMealById(dinnerId);
                return dinner;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        } while (true);
    }

    private static Meal getLunch(String nameOfDay) throws SQLException {
        List<Meal> lunchList = connection.getMealsByCategoryOrdered("lunch");
        Utils.showMeals(lunchList);
        Meal lunch;
        do {
            System.out.println("Choose the lunch for " + nameOfDay + " from the list above:");
            String input = scanner.nextLine();
            if (lunchList.stream().anyMatch(meal -> input.equals(meal.getName()))) {
                int lunchId = connection.getMealIdByName(input);
                lunch = connection.getMealById(lunchId);
                return lunch;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        } while (true);
    }

    private static Meal getBreakfast(String nameOfDay) throws SQLException {
        List<Meal> breakfastsList = connection.getMealsByCategoryOrdered("breakfast");
        Utils.showMeals(breakfastsList);
        Meal breakfast;

        do {
            System.out.println("Choose the breakfast for " + nameOfDay + " from the list above:");
            String input = scanner.nextLine();

            if (breakfastsList.stream().anyMatch(meal -> input.equals(meal.getName()))) {
                int breakfastId = connection.getMealIdByName(input);
                breakfast = connection.getMealById(breakfastId);
                return breakfast;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }

        } while (true);
    }


    private static String[] getMealsNames(List<Meal> meals) {
        String[] names = new String[meals.size()];
        return null;
    }
}
