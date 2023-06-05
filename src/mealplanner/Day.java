package mealplanner;

public class Day {
    private String name;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;

    public Day(String name, Meal breakfast, Meal lunch, Meal dinner) {
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getName() {
        return name;
    }

    public void printDay() {
        System.out.println(this.getName());
        System.out.println("Breakfast: " + this.breakfast.getName());
        System.out.println("Lunch: " + this.lunch.getName());
        System.out.println("Dinner: " + this.dinner.getName());
    }
}
