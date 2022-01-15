package hr.java.production.main;

import hr.java.production.model.*;
import hr.java.production.utils.InputUtils;

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
        Category[] categories = getCategoryInputs(scanner);
        Item[] items = getItemInputs(scanner, Arrays.asList(categories));
        Factory[] factories = getFactoryInputs(scanner, Arrays.asList(items));
        Store[] stores = getStoreInputs(scanner, Arrays.asList(items));

        System.out.println();
        findLargestItemFactories(Arrays.asList(items), Arrays.asList(factories));
        System.out.println();
        findCheapestItemStore(Arrays.asList(items), Arrays.asList(stores));
        System.out.println();
        getSortedCategories(Arrays.asList(categories), Arrays.asList(items));
    }

    private static Category[] getCategoryInputs(Scanner scanner) {
        Category[] categories = new Category[NUM_CATEGORY_INPUTS];
        for(int i = 0; i < NUM_CATEGORY_INPUTS; i++) {
            BigDecimal index = InputUtils.getNumberInput(scanner, i, "category index: ");
            String name = InputUtils.getStringInput(scanner, i, "category name: ");
            String description = InputUtils.getStringInput(scanner, i, "category description: ");
            categories[i] = new Category(name, description, index.intValue());
        }
        return categories;
    }

    private static Item[] getItemInputs(Scanner scanner, List<Category> categories) {
        Item[] items = new Item[NUM_ITEM_INPUTS];
        for(int i = 0; i < NUM_ITEM_INPUTS; i++) {
            BigDecimal index = InputUtils.getNumberInput(scanner, i, "item index: ");
            String name = InputUtils.getStringInput(scanner, i, "item name: ");
            BigDecimal width = InputUtils.getNumberInput(scanner, i, "item width: ");
            BigDecimal height = InputUtils.getNumberInput(scanner, i, "item height: ");
            BigDecimal length = InputUtils.getNumberInput(scanner, i, "item length: ");
            BigDecimal productionCost = InputUtils.getNumberInput(scanner, i, "item production cost: ");
            BigDecimal sellingPrice = InputUtils.getNumberInput(scanner, i, "item selling price: ");

            Category category = InputUtils.getListSelectionInput(scanner, i, Item.class, categories, false);

            Item item = new Item(name, category, width, height, length, productionCost, sellingPrice, index.intValue());
            items[i] = item;
        }
        return items;
    }

    private static Factory[] getFactoryInputs(Scanner scanner, List<Item> items) {
        Factory[] factories = new Factory[NUM_FACTORY_INPUTS];
        for(int i = 0; i < NUM_FACTORY_INPUTS; i++) {
            BigDecimal index = InputUtils.getNumberInput(scanner, i, "factory index: ");
            String name = InputUtils.getStringInput(scanner, i, "factory name: ");
            Address address = getAddressInput(scanner, i, Factory.class);
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Factory.class, items).toArray(new Item[0]);
            factories[i] = new Factory(name, address, selectedItems, index.intValue());
        }
        return factories;
    }

    private static Address getAddressInput(Scanner scanner, int i, Class<?> addressForClass) {
        BigDecimal index = InputUtils.getNumberInput(scanner, i, "address index: ");
        String addressFor = addressForClass.getSimpleName().toLowerCase();
        String street = InputUtils.getStringInput(scanner, i, addressFor + " address street: ");
        String houseNumber = InputUtils.getStringInput(scanner, i, addressFor + " house number: ");
        String city = InputUtils.getStringInput(scanner, i, addressFor + " address city: ");
        String postalCode = InputUtils.getStringInput(scanner, i, addressFor + " address postal code: ");
        return new Address(street, houseNumber, city, postalCode, index.intValue());
    }

    private static Store[] getStoreInputs(Scanner scanner, List<Item> items) {
        Store[] stores = new Store[NUM_STORE_INPUTS];
        for(int i = 0; i < NUM_STORE_INPUTS; i++) {
            BigDecimal index = InputUtils.getNumberInput(scanner, i, "store index: ");
            String name = InputUtils.getStringInput(scanner, i, "store name: ");
            String webAddress = InputUtils.getStringInput(scanner, i, "store web address: ");
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Store.class, items).toArray(new Item[0]);
            stores[i] = new Store(name, webAddress, selectedItems, index.intValue());
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

    private static void getSortedCategories(List<Category> categories, List<Item> items) {
        System.out.println("Sorted categories: ");
        List<Category> categoryList = new ArrayList<>(categories); //Keep original items list, could be removed
        categoryList.sort(Comparator.comparing(Category::getIndex));
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println("\t" + categoryList.get(i).toShortString());
            final int j = i;
            List<Item> copyItems = new ArrayList<>(items);
            List<Item> itemsForCategory = copyItems.stream()
                    .filter(item -> item.getCategory().equals(categoryList.get(j)))
                    .collect(Collectors.toList());

            if(itemsForCategory.isEmpty()) {
                System.out.println("\t\t- No items of this category");
                continue;
            }

            System.out.println("\t\t- Items for this category: ");
            for(Item item : itemsForCategory)
                System.out.println("\t\t\t --> " + item.toShortString());
        }
    }
}
