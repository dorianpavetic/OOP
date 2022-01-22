package hr.java.production.model;

/**
 * Produces items of type {@link Item}.
 */
public class Factory extends NamedEntity {
    private Address address;
    private Item[] items;

    /**
     * Creates new factory with name, address and items it produces.
     *
     * @param name name of the store.
     * @param address address of the store.
     * @param items items that this store sells.
     */
    public Factory(String name, Address address, Item[] items) {
        super(name);
        this.address = address;
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return Factory.class.getSimpleName()
                .concat(" ")
                .concat(getName())
                .concat(" produces ")
                .concat(String.valueOf(items.length))
                .concat(" items. ")
                .concat(address.toString());
    }
}
