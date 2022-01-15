package hr.java.production.model;

public class Category extends Printable implements Displayable {
    private String name;
    private String description;

    public Category(String name, String description, Integer index) {
        super(index);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .concat(name)
                .concat(" (")
                .concat(description)
                .concat(")");
    }

    @Override
    public String toShortString() {
        return "" + getIndex() + ". " + this;
    }
}
