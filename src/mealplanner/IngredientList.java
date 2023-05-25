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


    public int getId() {
        return id;
    }

    public String getIngredientsString() {
        return String.join(", ", list);
    }

}
