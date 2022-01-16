package hr.java.production.main;

import hr.java.production.model.*;
import hr.java.production.utils.InputUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static final String CURRENCY_UNIT = "HRK";
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
        printEdiblesInfo(List.of(items));
        System.out.println();
        printShortestWarrantyDurationTechnical(List.of(items));
    }

    private static Category[] getCategoryInputs(Scanner scanner) {
        Category[] categories = new Category[NUM_CATEGORY_INPUTS];
        for(int i = 0; i < NUM_CATEGORY_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "category name: ");
            String description = InputUtils.getStringInput(scanner, i, "category description: ");
            categories[i] = new Category(name, description);
        }
        return categories;
    }

    private static Item[] getItemInputs(Scanner scanner, List<Category> categories) {
        Item[] items = new Item[NUM_ITEM_INPUTS];
        for(int i = 0; i < NUM_ITEM_INPUTS; i++) {
            String selection = InputUtils.getListSelectionInput(scanner,
                    "Do you want to select food or others item as next item? Select on of the following",
                    Arrays.asList("Food", "Laptop", "Others"), false);

            String foodSelection = null;
            if(selection.equals("Food")) {
                foodSelection = InputUtils.getListSelectionInput(scanner,
                        "Select one of the following foods items",
                        Arrays.asList(Pasta.class.getSimpleName(), Pork.class.getSimpleName()), false);
            }

            String name = InputUtils.getStringInput(scanner, i, "item name: ");
            Category category = InputUtils.getListSelectionInput(scanner, i, Item.class, categories, false);
            BigDecimal productionCost = InputUtils.getNumberInput(scanner, i, "item production cost: ");
            BigDecimal sellingPrice = InputUtils.getNumberInput(scanner, i, "item selling price: ");
            BigDecimal discountInput = InputUtils.getNumberInput(scanner, i, "item discount: ", 0, 100);
            Discount discount = new Discount(discountInput);

            final Item item;
            BigDecimal width = BigDecimal.ZERO;
            BigDecimal height = BigDecimal.ZERO;
            BigDecimal length = BigDecimal.ZERO;
            if(foodSelection == null) {
                width = InputUtils.getNumberInput(scanner, i, "item width: ");
                height = InputUtils.getNumberInput(scanner, i, "item height: ");
                length = InputUtils.getNumberInput(scanner, i, "item length: ");

                if(selection.equals("Laptop")) {
                    BigInteger warrantyDuration = InputUtils.getNumberInput(scanner, i, "laptop item warranty: ").toBigInteger();
                    item = new Laptop(name, category, width, height, length, productionCost, sellingPrice, discount, warrantyDuration);
                }
                else
                    item = new Item(name, category, width, height, length, productionCost, sellingPrice, discount);
            }
            else {
                BigDecimal weight = InputUtils.getNumberInput(scanner, i, "edible item weight: ");
                if(foodSelection.equals(Pasta.class.getSimpleName()))
                    item = new Pasta(name, category, width, height, length, productionCost, sellingPrice, discount, weight);
                else
                    item = new Pork(name, category, width, height, length, productionCost, sellingPrice, discount, weight);

                Edible edible = (Edible) item;
                System.out.println("Entered food item has " + edible.calculateKilocalories() + " kilokalorija.");
                System.out.println("Total price of this food item is " + edible.calculatePrice() + " " + CURRENCY_UNIT);
            }

            items[i] = item;
        }
        return items;
    }

    private static Factory[] getFactoryInputs(Scanner scanner, List<Item> items) {
        Factory[] factories = new Factory[NUM_FACTORY_INPUTS];
        for(int i = 0; i < NUM_FACTORY_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "factory name: ");
            Address address = getAddressInput(scanner, i, Factory.class);
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Factory.class, items).toArray(new Item[0]);
            factories[i] = new Factory(name, address, selectedItems);
        }
        return factories;
    }

    private static Address getAddressInput(Scanner scanner, int i, Class<?> addressForClass) {
        String addressFor = addressForClass.getSimpleName().toLowerCase();
        String street = InputUtils.getStringInput(scanner, i, addressFor + " address street: ");
        String houseNumber = InputUtils.getStringInput(scanner, i, addressFor + " house number: ");
        String city = InputUtils.getStringInput(scanner, i, addressFor + " address city: ");
        String postalCode = InputUtils.getStringInput(scanner, i, addressFor + " address postal code: ");
        return new Address.Builder()
                .street(street)
                .houseNumber(houseNumber)
                .city(city)
                .postalCode(postalCode)
                .build();
    }

    private static Store[] getStoreInputs(Scanner scanner, List<Item> items) {
        Store[] stores = new Store[NUM_STORE_INPUTS];
        for(int i = 0; i < NUM_STORE_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "store name: ");
            String webAddress = InputUtils.getStringInput(scanner, i, "store web address: ");
            Item[] selectedItems = InputUtils.getListSelectionInputs(scanner, i, Store.class, items).toArray(new Item[0]);
            stores[i] = new Store(name, webAddress, selectedItems);
        }
        return stores;
    }

    private static void findLargestItemFactories(List<Item> items, List<Factory> factories) {
        Item largestVolumeItem =
                items
                        .stream()
                        .max(Comparator.comparing(Item::getVolume))
                        .orElse(null);
        if(largestVolumeItem == null) {
            System.out.println("Cannot determine highest volume item.");
            return;
        }

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
                System.out.println("\t" + (i+1) + ". " + largestVolumeItemFactories.get(i).toString());
        }
    }

    private static void findCheapestItemStore(List<Item> items, List<Store> stores) {
        Item cheapestItem =
                items
                        .stream()
                        .min(Comparator.comparing(Item::getSellingPrice))
                        .orElse(null);
        if(cheapestItem == null) {
            System.out.println("Cannot determine cheapest item.");
            return;
        }

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
                System.out.println("\t" + (i+1) + ". " + cheapestItemStores.get(i).toString());
        }
    }

    private static void printEdiblesInfo(List<Item> items) {
        List<Edible> edibles =
                items
                        .stream()
                        .filter(item -> item instanceof Edible)
                        .map(item -> (Edible) item)
                        .collect(Collectors.toList());

        Edible highestCaloriesEdible =
                edibles
                        .stream()
                        .max(Comparator.comparing(Edible::calculateKilocalories))
                        .orElse(null);

        Edible mostExpensiveEdible =
                edibles
                        .stream()
                        .max(Comparator.comparing(Edible::calculatePrice))
                        .orElse(null);

        if(highestCaloriesEdible != null) {
            System.out.println("Highest calories edible item: ");
            System.out.println("\t- " + highestCaloriesEdible);
            System.out.println("Most expensive edible item: ");
            System.out.println("\t- " + mostExpensiveEdible);
        }
        else
            System.out.println("No edible food items.");
    }

    private static void printShortestWarrantyDurationTechnical(List<Item> items) {
        List<Technical> technicals =
                items
                        .stream()
                        .filter(item -> item instanceof Technical)
                        .map(item -> (Technical) item)
                        .collect(Collectors.toList());

        Technical shortestWarrantyDurationTechnical =
                technicals
                        .stream()
                        .min(Comparator.comparing(Technical::getWarrantyDuration))
                        .orElse(null);

        if(shortestWarrantyDurationTechnical != null) {
            System.out.println("Shortest warranty duration technical item: ");
            System.out.println("\t- " + shortestWarrantyDurationTechnical);
        }
        else
            System.out.println("No technical items.");
    }
}
