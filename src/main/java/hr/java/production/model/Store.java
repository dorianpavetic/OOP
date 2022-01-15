package hr.java.production.model;

import java.util.Arrays;

public class Store extends Printable implements Displayable  {
    private String name;
    private String webAddress;
    private Item[] items;

    public Store(String name, String webAddress, Item[] items, Integer index) {
        super(index);
        this.name = name;
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .concat(name)
                .concat(", Web address: ")
                .concat(webAddress)
                .concat(", Items: ")
                .concat(Arrays.toString(items));
    }

    @Override
    public String toShortString() {
        return Store.class.getSimpleName()
                .concat(" ")
                .concat(name)
                .concat(" sells ")
                .concat(String.valueOf(items.length))
                .concat(" items")
                .concat(". Web address: ")
                .concat(webAddress);
    }
}
