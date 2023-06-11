package mealplanner;

import java.util.List;

public class IngredientList {

    private final int id;
    List<String> list;

    public IngredientList(List<String> ingredientsList) {
        this.id = Utils.generateRandomId();
        this.list = ingredientsList;
    }

    public IngredientList(String[] ingredientsArray) {
        this.id = Utils.generateRandomId();
        this.list = List.of(ingredientsArray);
    }

    public IngredientList(String ingredientsString, int id) {
        this.id = id;
        this.list = List.of(Meal.parseIngredientsString(ingredientsString));
    }


    public int getId() {
        return id;
    }

    public List<String> getList() {
        return list;
    }

    public String getIngredientsString() {
        return String.join(", ", list);
    }

}
