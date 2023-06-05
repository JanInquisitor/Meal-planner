package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseConnection {
    private final String DB_URL = "jdbc:postgresql:meals_db";
    private final String USER = "postgres";
    private final String PASS = "1111";


    public static DatabaseConnection createDatabaseConnection() {
        return new DatabaseConnection();
    }

    public void storeMeal(String category, String meal, int mealId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             // Insert created meal into database
             PreparedStatement statement = connection.prepareStatement("INSERT INTO meals (category, meal, meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, category);
            statement.setString(2, meal);
            statement.setInt(3, mealId);

            statement.executeUpdate();
        }
    }

    public void storeIngredients(String ingredient, int ingredientId, int mealId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredients (ingredient, ingredient_id ,meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, ingredient);
            statement.setInt(2, ingredientId);
            statement.setInt(3, mealId);

            statement.executeUpdate();
        }
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

                    return new Meal(category, mealName, ingredients);

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

                    Meal meal = new Meal(mealCategory, mealName, ingredientList);

                    mealsList.add(meal);

                }

            }

        }

        return mealsList;
    }

    public List<Meal> getMealsByCategoryOrdered(String category) throws SQLException {

        List<Meal> mealsList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE category = ? ORDER BY meal")) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    String mealName = resultSet.getString("meal");

                    String mealCategory = resultSet.getString("category");

                    int mealId = resultSet.getInt("meal_id");

                    IngredientList ingredientList = this.getIngredientsByMealId(mealId);

                    Meal meal = new Meal(mealCategory, mealName, ingredientList);

                    mealsList.add(meal);

                }

            }

        }

        return mealsList;
    }

    public List<Meal> getAllMeals() throws SQLException {
        List<Meal> mealsList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM meals");

            while (resultSet.next()) {

                String category = resultSet.getString("category");

                String mealName = resultSet.getString("meal");

                int mealId = resultSet.getInt("meal_id");

                IngredientList ingredientList = this.getIngredientsByMealId(mealId);

                Meal meal = new Meal(category, mealName, ingredientList);

                mealsList.add(meal);
            }

        }

        return mealsList;
    }

    // This method save individual plans (or days)
    public void saveDayPlanToDb(String mealName, String category, int mealId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO plan (meal_option, meal_category, meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, mealName);
            statement.setString(2, category);
            statement.setInt(3, mealId);

            statement.executeUpdate();
        }
    }

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

            // Creating 'plan' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan (\n" +
                    "  meal_option VARCHAR(255),\n" +
                    "  meal_category VARCHAR(255),\n" +
                    "  meal_id INT\n" +
                    ")");

        }
    }


    public void createAndPrintMealsTable() throws SQLException {
        // Create a Properties object and set the properties
        Properties props = new Properties();
        props.setProperty("user", this.USER);
        props.setProperty("pass", this.PASS);

        // Create connection
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        // Create statement and query
        Statement statement = connection.createStatement();
        statement.executeUpdate("drop table if exists meals");
        statement.executeUpdate("create table meals (id integer, name varchar(80) NOT NULL)");
        statement.executeUpdate("insert into meals (id, name) values (1, 'sushi')");
        statement.executeUpdate("insert into meals (id, name) values (2, 'philly cheese steak')");

        // Executes query
        ResultSet rs = statement.executeQuery("select * from meals");

        // Read the result set

        while (rs.next()) {
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getString("id"));
        }
        statement.close();
        connection.close();
    }

}