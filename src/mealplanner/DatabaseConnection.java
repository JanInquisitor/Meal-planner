package mealplanner;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private String DB_URL = "jdbc:postgresql:meals_db";
    private String USER = "postgres";
    private String PASS = "1111";


    public static DatabaseConnection createDatabaseConnection() {
        return new DatabaseConnection();
    }

    public void storeMeal(String category, String meal, int mealId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO meals (category, meal, meal_id) VALUES (?, ?, ?)")) {

            statement.setString(1, category);
            statement.setString(2, meal);
            statement.setInt(3, mealId);
            statement.executeUpdate();
        }
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
//            ResultSet ingredientsResultSet = statement.executeQuery("SELECT * FROM ingredients");
//            while (ingredientsResultSet.next()) {
//                String ingredient = ingredientsResultSet.getString("ingredient");
//                int ingredientId = ingredientsResultSet.getInt("ingredient_id");
//                int mealId = ingredientsResultSet.getInt("meal_id");
//                System.out.println("Ingredient: " + ingredient + ", Ingredient ID: " + ingredientId + ", Meal ID: " + mealId);
//            }
//            ingredientsResultSet.close();
        }
    }

    public void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            // Creating 'meals' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals ("
                    + "category VARCHAR(255),"
                    + "meal VARCHAR(255),"
                    + "meal_id INT"
                    + ")");

            // Creating 'ingredients' table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients ("
                    + "ingredient VARCHAR(255),"
                    + "ingredient_id INT,"
                    + "meal_id INT"
                    + ")");
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