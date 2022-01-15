package hr.java.production.model;

import java.util.Arrays;

public class Factory extends Printable implements Displayable {
    private String name;
    private Address address;
    private Item[] items;

    public Factory(String name, Address address, Item[] items, Integer index) {
        super(index);
        this.name = name;
        this.address = address;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .concat(name)
                .concat(", ")
                .concat(address.toString())
                .concat(", Items: ")
                .concat(Arrays.toString(items));
    }

    @Override
    public String toShortString() {
        return Factory.class.getSimpleName()
                .concat(" ")
                .concat(name)
                .concat(" produces ")
                .concat(String.valueOf(items.length))
                .concat(" items. ")
                .concat(address.toString());
    }
}
