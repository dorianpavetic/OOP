package hr.java.production.model;

public class Category extends NamedEntity {
    private String description;

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
