package hr.java.production.model;

import java.util.Objects;
import java.util.Set;

/**
 * Produces items of type {@link Item}.
 */
public class Factory extends NamedEntity {
    private Address address;
    private Set<Item> items;

    /**
     * Creates new factory with name, address and items it produces.
     *
     * @param name name of the store.
     * @param address address of the store.
     * @param items items that this store sells.
     */
    public Factory(String name, Address address, Set<Item> items) {
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return Factory.class.getSimpleName()
                .concat(" ")
                .concat(getName())
                .concat(" produces ")
                .concat(String.valueOf(items.size()))
                .concat(" items. ")
                .concat(address.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Factory factory = (Factory) o;
        return Objects.equals(address, factory.address) && Objects.equals(items, factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, items);
    }
}
