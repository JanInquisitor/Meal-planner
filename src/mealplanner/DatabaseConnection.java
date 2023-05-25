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

    public void storeIngredients(String ingredient, int ingredientId,int mealId) throws SQLException {
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

    public String[] getMealById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE meal_id = ?")) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String meal = resultSet.getString("meal");

                    String category = resultSet.getString("category");

                    int mealId = resultSet.getInt("meal_id");

                    return new String[]{meal, category, String.valueOf(mealId)};

                }
            }

        }

        return null;
    }

    public String[] getIngredientsByMealId(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredients WHERE meal_id = ?")) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String ingredientString = resultSet.getString("ingredient");

                    int mealId = resultSet.getInt("meal_id");

                    int ingredientId = resultSet.getInt("ingredient_id");

                    return new String[]{ingredientString, String.valueOf(mealId), String.valueOf(ingredientId)};
                }

            }
        }

        return null;
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

                String ingredientsString = this.getIngredientsByMealId(mealId)[0];

                String[] ingredientsArray = Meal.parseIngredientsString(ingredientsString);

                Meal meal = new Meal(category, mealName, List.of(ingredientsArray));

                mealsList.add(meal);
            }

        }

        return mealsList;
    }

    public void readData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            // Read data from 'meals' table
            ResultSet mealsResultSet = statement.executeQuery("SELECT * FROM meals");
            while (mealsResultSet.next()) {
                String category = mealsResultSet.getString("category");
                String meal = mealsResultSet.getString("meal");
                int mealId = mealsResultSet.getInt("meal_id");
                System.out.println("Category: " + category + ", Meal: " + meal + ", Meal ID: " + mealId);
            }
            mealsResultSet.close();

            // Read data from 'ingredients' table
            ResultSet ingredientsResultSet = statement.executeQuery("SELECT * FROM ingredients");
            while (ingredientsResultSet.next()) {
                String ingredient = ingredientsResultSet.getString("ingredient");
                int ingredientId = ingredientsResultSet.getInt("ingredient_id");
                int mealId = ingredientsResultSet.getInt("meal_id");
                System.out.println("Ingredient: " + ingredient + ", Ingredient ID: " + ingredientId + ", Meal ID: " + mealId);
            }
            ingredientsResultSet.close();
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