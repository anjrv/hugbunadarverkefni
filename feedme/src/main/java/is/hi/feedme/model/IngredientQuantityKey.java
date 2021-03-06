package is.hi.feedme.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embeddable composite primary key for the IngredientQuantity entity
 */
@Embeddable
public class IngredientQuantityKey implements Serializable {

    @Column(name = "ingredient_id")
    long ingredientId;

    @Column(name = "recipe_id")
    long recipeId;

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long id) {
        this.ingredientId = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long id) {
        this.recipeId = id;
    }
    
}
