package mealplanner;

// This class represent the Plan entity.
// Maybe this is too much memory but whatever.
// I could also use enums maybe...
public class Plan {

    Day[] week;

    public Plan(Day[] week) {
        this.week = week;
    }

    public Day[] getWeek() {
        return week;
    }
}
