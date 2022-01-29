package hr.java.production.model;

import java.util.Objects;

/**
 * Used to categorize items. Contains name and description.
 */
public class Category extends NamedEntity {
    private String description;

    /**
     * @param name name of the category.
     * @param description description of the category.
     */
    public Category(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Category.class.getSimpleName()
                .concat(" ")
                .concat(getName())
                .concat(" (")
                .concat(description)
                .concat(")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
