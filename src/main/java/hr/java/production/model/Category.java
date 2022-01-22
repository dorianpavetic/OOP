package hr.java.production.model;

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
}
