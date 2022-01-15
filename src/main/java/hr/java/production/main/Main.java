package hr.java.production.main;

import hr.java.production.utils.InputUtils;
import hr.java.production.model.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final int NUM_CATEGORY_INPUTS = 3;
    private static final int NUM_ITEM_INPUTS = 5;
    private static final int NUM_FACTORY_INPUTS = 2;
    private static final int NUM_STORE_INPUTS = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Category> categories = getCategoryInputs(scanner);
        List<Item> items = getItemInputs(scanner, categories);
        List<Factory> factories = getFactoryInputs(scanner, items);
        List<Store> stores = getStoreInputs(scanner, items);

        System.out.println();
        findLargestItemFactories(items, factories);
        System.out.println();
        findCheapestItemStore(items, stores);
    }

    private static List<Category> getCategoryInputs(Scanner scanner) {
        List<Category> categories = new ArrayList<>();
        for(int i = 0; i < NUM_CATEGORY_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "category name: ");
            String description = InputUtils.getStringInput(scanner, i, "category description: ");
            categories.add(new Category(name, description));
        }
        return categories;
    }

    private static List<Item> getItemInputs(Scanner scanner, List<Category> categories) {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < NUM_ITEM_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "item name: ");
            BigDecimal width = InputUtils.getNumberInput(scanner, i, "item width: ");
            BigDecimal height = InputUtils.getNumberInput(scanner, i, "item height: ");
            BigDecimal length = InputUtils.getNumberInput(scanner, i, "item length: ");
            BigDecimal productionCost = InputUtils.getNumberInput(scanner, i, "item production cost: ");
            BigDecimal sellingPrice = InputUtils.getNumberInput(scanner, i, "item selling price: ");

            Category category = InputUtils.getListSelectionInput(scanner, i, Item.class, categories, false);

            Item item = new Item(name, category, width, height, length, productionCost, sellingPrice);
            items.add(item);
        }
        return items;
    }

    private static List<Factory> getFactoryInputs(Scanner scanner, List<Item> items) {
        List<Factory> factories = new ArrayList<>();
        for(int i = 0; i < NUM_FACTORY_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "factory name: ");
            Address address = getAddressInput(scanner, i, Factory.class);
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Factory.class, items).toArray(new Item[0]);
            factories.add(new Factory(name, address, selectedItems));
        }
        return factories;
    }

    private static Address getAddressInput(Scanner scanner, int i, Class<?> addressForClass) {
        String addressFor = addressForClass.getSimpleName().toLowerCase();
        String street = InputUtils.getStringInput(scanner, i, addressFor + " address street: ");
        String houseNumber = InputUtils.getStringInput(scanner, i, addressFor + " house number: ");
        String city = InputUtils.getStringInput(scanner, i, addressFor + " address city: ");
        String postalCode = InputUtils.getStringInput(scanner, i, addressFor + " address postal code: ");
        return new Address(street, houseNumber, city, postalCode);
    }

    private static List<Store> getStoreInputs(Scanner scanner, List<Item> items) {
        List<Store> stores = new ArrayList<>();
        for(int i = 0; i < NUM_STORE_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "store name: ");
            String webAddress = InputUtils.getStringInput(scanner, i, "store web address: ");
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Store.class, items).toArray(new Item[0]);
            stores.add(new Store(name, webAddress, selectedItems));
        }
        return stores;
    }

    private static void findLargestItemFactories(List<Item> items, List<Factory> factories) {
        List<Item> itemList = new ArrayList<>(items); //Keep original items list, could be removed
        //Sorting input item list instead of item list from factories because
        //it is not guaranteed that any factory produces the largest volume item.
        //Also, multiple factories could produce that item.
        itemList.sort((o1, o2) -> o2.getVolume().compareTo(o1.getVolume()));
        Item largestVolumeItem = itemList.get(0);
        List<Factory> largestVolumeItemFactories =
                factories
                        .stream()
                        .filter(factory -> Arrays.asList(factory.getItems()).contains(largestVolumeItem))
                        .collect(Collectors.toList());

        if(largestVolumeItemFactories.isEmpty())
            System.out.println("None of the factories produce item with the largest volume (" +
                    largestVolumeItem.getName() + ", Volume: " + largestVolumeItem.getVolume() + ")");
        else {
            System.out.println("The following factories produce largest volume item (" +
                    largestVolumeItem.getName() + ", Volume: " + largestVolumeItem.getVolume() + "): ");
            for (int i = 0; i < largestVolumeItemFactories.size(); i++)
                System.out.println("\t" + (i+1) + ". " + largestVolumeItemFactories.get(i).toShortString());
        }
    }

    private static void findCheapestItemStore(List<Item> items, List<Store> stores) {
        List<Item> itemList = new ArrayList<>(items); //Keep original items list, could be removed
        //Sorting input item list instead of item list from factories because
        //it is not guaranteed that any factory produces the largest volume item.
        //Also, multiple factories could produce that item.
        itemList.sort(Comparator.comparing(Item::getSellingPrice));
        Item cheapestItem = itemList.get(0);
        List<Store> cheapestItemStores =
                stores
                        .stream()
                        .filter(factory -> Arrays.asList(factory.getItems()).contains(cheapestItem))
                        .collect(Collectors.toList());

        if(cheapestItemStores.isEmpty())
            System.out.println("None of the stores sell the cheapest item (" +
                    cheapestItem.getName() + ", Selling price: " + cheapestItem.getSellingPrice() + ")");
        else {
            System.out.println("The following stores sell the cheapest item (" +
                    cheapestItem.getName() + ", Selling price: " + cheapestItem.getSellingPrice() + "): ");
            for (int i = 0; i < cheapestItemStores.size(); i++)
                System.out.println("\t" + (i+1) + ". " + cheapestItemStores.get(i).toShortString());
        }
    }
}
