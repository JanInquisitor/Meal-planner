package mealplanner;

import java.sql.*;
import java.util.*;

public class DatabaseConnection implements MealDao {
    private final String DB_URL = "jdbc:postgresql:meals_db";
    private final String USER = "postgres";
    private final String PASS = "1111";


    public static DatabaseConnection createDatabaseConnection() {
        return new DatabaseConnection();
    }

    public int getMealIdByName(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE meal = ?")) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("meal_id");
                }
            }
        }

        return -1;
    }

    public Meal getMealById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE meal_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String mealName = resultSet.getString("meal");
                    String category = resultSet.getString("category");
                    int mealId = resultSet.getInt("meal_id");
                    IngredientList ingredients = this.getIngredientsByMealId(mealId);
                    return new Meal(id ,category, mealName, ingredients);
                }
            }
        }
        return null;
    }

    public IngredientList getIngredientsByMealId(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredients WHERE meal_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String ingredientString = resultSet.getString("ingredient");
//                    int mealId = resultSet.getInt("meal_id");
                    int ingredientId = resultSet.getInt("ingredient_id");
                    return new IngredientList(ingredientString, ingredientId);
                }
            }
        }
        return null;
    }


    public List<Meal> getMealsByCategory(String category) throws SQLException {
        List<Meal> mealsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE category = ?")) {
            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String mealName = resultSet.getString("meal");
                    String mealCategory = resultSet.getString("category");
                    int mealId = resultSet.getInt("meal_id");
                    IngredientList ingredientList = this.getIngredientsByMealId(mealId);
                    Meal meal = new Meal(mealId, mealCategory, mealName, ingredientList);
                    mealsList.add(meal);
                }
            }
        }
        return mealsList;
    }

    public List<Meal> getMealsByCategory(String category, boolean ordered) throws SQLException {
        List<Meal> mealsList = new ArrayList<>();
        String query;
        if (ordered) {
            query = "SELECT * FROM meals WHERE category = ? ORDER BY meal";
        } else {
            query = "SELECT * FROM meals WHERE category = ?";
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String mealName = resultSet.getString("meal");
                    String mealCategory = resultSet.getString("category");
                    int mealId = resultSet.getInt("meal_id");
                    IngredientList ingredientList = this.getIngredientsByMealId(mealId);
                    Meal meal = new Meal(mealId, mealCategory, mealName, ingredientList);
                    mealsList.add(meal);
                }
            }
        }
        return mealsList;
    }

    // This method save individual plans (or days)
    public void saveDayPlanToDb(String dayOfWeek, String mealName, String category, int mealId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO plan (day, meal_option, meal_category, meal_id) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, dayOfWeek);
            statement.setString(2, mealName);
            statement.setString(3, category);
            statement.setInt(4, mealId);

            statement.executeUpdate();
        }
    }

    public void savePlan(Plan plan) throws SQLException {

        Day[] week = plan.getWeek();

        for (Day day : week) {

            Meal[] meals = day.getMeals();

            for (Meal meal : meals) {
                this.saveDayPlanToDb(day.getName(), meal.getName(), meal.getCategory(), meal.getId());
            }

        }

    }

    public Plan getPlans() throws SQLException {
        List<Day> days = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan")) {

            ResultSet resultSet = statement.executeQuery();

            Map<String, List<Meal>> mealsByDay = new HashMap<>();

            while (resultSet.next()) {
                String dayOfWeek = resultSet.getString("day");
                String mealName = resultSet.getString("meal_option");
                String category = resultSet.getString("meal_category");
                int mealId = resultSet.getInt("meal_id");

                Meal meal = getMealById(mealId); // Replace 'null' with appropriate method call to retrieve the Meal object

                mealsByDay.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(meal);
            }

            for (Map.Entry<String, List<Meal>> entry : mealsByDay.entrySet()) {
                String dayOfWeek = entry.getKey();
                List<Meal> meals = entry.getValue();

                Day day = new Day(dayOfWeek, meals.toArray(new Meal[0]));
                days.add(day);
            }
        }

        return new Plan(days.toArray(new Day[0]));
    }


//    public Plan getPlans() throws SQLException {
//        List<Day> days = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan")) {
//
//            ResultSet resultSet = statement.executeQuery();
//
//            Map<String, List<Meal>> mealsByDay = new HashMap<>();
//
//            while (resultSet.next()) {
//                String dayOfWeek = resultSet.getString("day");
//                String mealName = resultSet.getString("meal_option");
//                String category = resultSet.getString("meal_category");
//                int mealId = resultSet.getInt("meal_id");
//
//                IngredientList ingredients = this.getIngredientsByMealId(mealId);
//
//                Meal meal = new Meal(mealId, category, mealName, ingredients); // Replace 'null' with appropriate IngredientList
//
//                mealsByDay.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(meal);
//            }
//
//            for (Map.Entry<String, List<Meal>> entry : mealsByDay.entrySet()) {
//                String dayOfWeek = entry.getKey();
//                List<Meal> meals = entry.getValue();
//
//                Meal[] mealsArray = meals.toArray(new Meal[0]);
//                Day day = new Day(dayOfWeek);
//                if (mealsArray.length >= 1) {
//                    day.setBreakfast(mealsArray[0]);
//                }
//                if (mealsArray.length >= 2) {
//                    day.setLunch(mealsArray[1]);
//                }
//                if (mealsArray.length >= 3) {
//                    day.setDinner(mealsArray[2]);
//                }
//
//                days.add(day);
//            }
//        }
//
//        return new Plan(days.toArray(new Day[0]));
//    }

    public void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            // Creating 'meals' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals (\n" +
                    "  category VARCHAR(255),\n" +
                    "  meal VARCHAR(255),\n" +
                    "  meal_id INT\n" +
                    ")");

            // Creating 'ingredients' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (\n" +
                    "  ingredient VARCHAR(255),\n" +
                    "  ingredient_id INT,\n" +
                    "  meal_id INT\n" +
                    ")");

        }
    }

    public void createPlanTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            // Creating 'plan' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan (\n" +
                    "  day VARCHAR(255),\n" +
                    "  meal_option VARCHAR(255),\n" +
                    "  meal_category VARCHAR(255),\n" +
                    "  meal_id INT\n" +
                    ")");
        }

    }

    public void dropPlanTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS plan");
        }
    }

    @Override
    public List<Meal> findAll() throws SQLException {
        List<Meal> mealsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM meals");
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String mealName = resultSet.getString("meal");
                int mealId = resultSet.getInt("meal_id");
                IngredientList ingredientList = this.getIngredientsByMealId(mealId);
                Meal meal = new Meal(mealId,category, mealName, ingredientList);
                mealsList.add(meal);
            }
        }
        return mealsList;
    }

    @Override
    public Meal findById(int id) {
        return null;
    }

    @Override
    public void add(Meal meal) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             // Insert created meal into database
             PreparedStatement statement = connection.prepareStatement("INSERT INTO meals (category, meal, meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, meal.getCategory());
            statement.setString(2, meal.getName());
            statement.setInt(3, meal.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeIngredients(IngredientList ingredients, int mealId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredients (ingredient, ingredient_id ,meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, ingredients.getIngredientsString());
            statement.setInt(2, ingredients.getId());
            statement.setInt(3, mealId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public void deleteById(int id) {

    }


}
