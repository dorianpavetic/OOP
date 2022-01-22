package hr.java.production.model;

/**
 * Used for entities that need to have name field.
 */
public abstract class NamedEntity {
    private String name;

    /**
     * Create new NamedEntity with provided name.
     *
     * @param name entity name.
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
