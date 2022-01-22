package hr.java.production.model;

/**
 * Sells items of type {@link Item}.
 */
public class Store extends NamedEntity {
    private String webAddress;
    private Item[] items;

    /**
     * Creates new store with name, web address and selling items.
     *
     * @param name name of the store.
     * @param webAddress web address of the store.
     * @param items items that this store sells.
     */
    public Store(String name, String webAddress, Item[] items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return Store.class.getSimpleName()
                .concat(" ")
                .concat(getName())
                .concat(" sells ")
                .concat(String.valueOf(items.length))
                .concat(" items")
                .concat(". Web address: ")
                .concat(webAddress);
    }
}
