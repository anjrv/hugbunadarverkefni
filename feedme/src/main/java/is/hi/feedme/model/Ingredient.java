package is.hi.feedme.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Ingredient entity, tied to the ingredients table in the database
 * Additional relations:
 * * One to many relationship with the ingredient_quantities connecting table
 * ( This additional relational table is used to store ingredient quantities and can be observed in the IngredientQuantity object )
 * </pre>
 */
@Entity
@Table(name = "ingredients")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<IngredientQuantity> ingredientQuantities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientQuantity> getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setRecipes(Set<IngredientQuantity> ingredientQuantities) {
        this.ingredientQuantities = ingredientQuantities;
    }

}
