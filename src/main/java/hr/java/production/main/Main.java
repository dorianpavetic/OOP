package hr.java.production.main;

import hr.java.production.exception.IllegalMeasuringUnitArgumentException;
import hr.java.production.exception.MultipleCategoryNamesException;
import hr.java.production.exception.NegativeNumberOperationException;
import hr.java.production.model.*;
import hr.java.production.utils.InputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Runs app and contains main operations about app.
 */
public class Main {
    public static final String CURRENCY_UNIT = "HRK";
    private static final int NUM_CATEGORY_INPUTS = 3;
    private static final int NUM_ITEM_INPUTS = 5;
    private static final int NUM_FACTORY_INPUTS = 2;
    private static final int NUM_STORE_INPUTS = 2;
    private static final int NUM_UNIT_INPUTS = 3;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Lets user input categories, items, factories and stores.
     * After are fields are entered, print data about inputs with filtering
     * and sorting based on several fields.
     *
     * @param args default command line arguments that are not being used.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Category[] categories = getCategoryInputs(scanner);
        Item[] items = getItemInputs(scanner, Arrays.asList(categories));
        Factory[] factories = getFactoryInputs(scanner, Arrays.asList(items));
        Store[] stores = getStoreInputs(scanner, Arrays.asList(items));
        MeasuringUnit[] measuringUnits = getMeasuringUnitInputs(scanner);

        System.out.println();
        findLargestItemFactories(Arrays.asList(items), Arrays.asList(factories));
        System.out.println();
        findCheapestItemStores(Arrays.asList(items), Arrays.asList(stores));
        System.out.println();
        printEdiblesInfo(List.of(items));
        System.out.println();
        printShortestWarrantyDurationTechnical(List.of(items));

        System.out.println();
        printMeasuringUnits(List.of(measuringUnits));
    }

    /**
     * Prints list of all available measuring units' names.
     *
     * @param measuringUnits list of all entered measuring units.
     */
    private static void printMeasuringUnits(List<MeasuringUnit> measuringUnits) {
        System.out.println("Following measuring units have been entered:");
        for(MeasuringUnit measuringUnit : measuringUnits)
            System.out.println(measuringUnit.name());
    }

    /**
     * Gets inputs about measuring units gathering inputs about names.
     *
     * @param scanner allows inputs.
     * @return array of entered measuring units.
     */
    private static MeasuringUnit[] getMeasuringUnitInputs(Scanner scanner) {
        MeasuringUnit[] measuringUnits = new MeasuringUnit[NUM_UNIT_INPUTS];
        for(int i = 0; i < NUM_UNIT_INPUTS; i++) {
            String name = InputUtils.getStringInput(scanner, i, "measuring unit name: ");
            try {
                measuringUnits[i] = new MeasuringUnit(name);
            } catch (IllegalMeasuringUnitArgumentException ex) {
                System.out.println(ex.getMessage() + ". Please repeat...");
                ex.printStackTrace();
                logger.error("Blank measuring unit entered", ex);
                i--;
            }
        }
        return measuringUnits;
    }

    /**
     * Gets inputs about categories and return them as array.
     * Checks that all category names are unique, if not {@link MultipleCategoryNamesException}
     * is thrown on input and user needs to repeat input.
     *
     * @param scanner allows inputs.
     * @return array of entered categories.
     */
    private static Category[] getCategoryInputs(Scanner scanner) {
        Category[] categories = new Category[NUM_CATEGORY_INPUTS];
        for(int i = 0; i < NUM_CATEGORY_INPUTS; i++) {
            String categoryName = null;
            do {
                try {
                    categoryName = getCategoryNameInput(scanner, i, categories);
                } catch (MultipleCategoryNamesException ex) {
                    logger.error("Category is already entered before.", ex);
                    System.out.println("[ERROR] " + ex.getMessage() + ". Please enter category name with unique name..");
                }
            } while (categoryName == null);
            String description = InputUtils.getStringInput(scanner, i, "category description: ");
            categories[i] = new Category(categoryName, description);
        }
        return categories;
    }


    /**
     * Gets category name if unique string entered, otherwise throws {@link MultipleCategoryNamesException}.
     *
     * @param scanner allows inputs.
     * @param i index of category name is being entered for.
     * @param categories list of previously entered categories. Needed to check if new input is unique.
     * @return unique category name.
     * @throws MultipleCategoryNamesException if category with same name already exists.
     */
    private static String getCategoryNameInput(Scanner scanner, int i, Category[] categories)
            throws MultipleCategoryNamesException {

        String categoryName = InputUtils.getStringInput(scanner, i, "category name: ");
        for(Category category : categories) {
            if(category != null && category.getName().equals(categoryName))
                throw new MultipleCategoryNamesException("Multiple categories with name '" + categoryName + "'");
        }
        return categoryName;
    }

    /**
     * Gets inputs about items and return them as array.
     * User can choose between multiple kind of items - Food, Laptop and Others.
     *
     * @param scanner allows inputs.
     * @param categories entered categories which can be assigned to categories.
     * @return array of entered items.
     */
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
            if(foodSelection == null) {
                BigDecimal width = InputUtils.getNumberInput(scanner, i, "item width: ");
                BigDecimal height = InputUtils.getNumberInput(scanner, i, "item height: ");
                BigDecimal length = InputUtils.getNumberInput(scanner, i, "item length: ");

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
                    item = new Pasta(name, category, productionCost, sellingPrice, discount, weight);
                else
                    item = new Pork(name, category, productionCost, sellingPrice, discount, weight);

                Edible edible = (Edible) item;
                try {
                    System.out.println("Entered food item has " + edible.calculateKilocalories() + " kilocalories.");
                } catch (NegativeNumberOperationException ex) {
                    System.out.println("Cannot calculate kilocalories for this item. " + ex.getMessage());
                    ex.printStackTrace();
                    logger.error(MarkerFactory.getMarker("FATAL"), ex.getMessage(), ex);

                }

                try {
                    System.out.println("Total price of this food item is " + edible.calculatePrice() + " " + CURRENCY_UNIT);
                } catch (NegativeNumberOperationException ex) {
                    System.out.println("Cannot calculate total price of this item. " + ex.getMessage());
                    ex.printStackTrace();}
            }

            items[i] = item;
        }
        return items;
    }

    /**
     * Gets inputs about factories and return them as array.
     *
     * @param scanner allows inputs.
     * @param items entered items which can be assigned to factories.
     * @return array of entered factories.
     */
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


    /**
     * Gets inputs about address and return new {@link Address}.
     *
     * @param scanner allows inputs.
     * @param i index of input in list which address is being entered for.
     * @param addressForClass object class that address is being entered for.
     * @return Address with street name, house number, city name and postal code.
     */
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

    /**
     * Gets inputs about stores and return them as array.
     *
     * @param scanner allows inputs.
     * @param items entered items which can be assigned to stores.
     * @return array of entered stores.
     */
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

    /**
     * Finds and prints the list of factories that produce item with the largest volume.
     *
     * @param items entered items to find the largest volume item.
     * @param factories entered factories to filter for the largest volume item.
     */
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

    /**
     * Finds and prints the list of stores that sells the cheapest item.
     *
     * @param items entered items to find the cheapest item.
     * @param stores entered stores to filter for the cheapest item.
     */
    private static void findCheapestItemStores(List<Item> items, List<Store> stores) {
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

    /**
     * Finds and prints data about the highest calories and the most expensive edibles.
     * In case there is no edibles exist among items, "No edible food items." is printed.
     *
     * @param items entered items to print data for.
     */
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
                        .max((o1, o2) -> {
                            BigInteger kilocalories1, kilocalories2;
                            try {
                                kilocalories1 = o1.calculateKilocalories();
                                kilocalories2 = o2.calculateKilocalories();
                            } catch (NegativeNumberOperationException ex) {
                                kilocalories1 = BigInteger.ZERO;
                                kilocalories2 = BigInteger.ZERO;
                                ex.printStackTrace();
                            }
                            return kilocalories1.compareTo(kilocalories2);
                        })
                        .orElse(null);

        Edible mostExpensiveEdible =
                edibles
                        .stream()
                        .max((o1, o2) -> {
                            BigInteger price1, price2;
                            try {
                                price1 = o1.calculateKilocalories();
                                price2 = o2.calculateKilocalories();
                            } catch (NegativeNumberOperationException ex) {
                                price1 = BigInteger.ZERO;
                                price2 = BigInteger.ZERO;
                                ex.printStackTrace();

                            }
                            return price1.compareTo(price2);
                        })
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

    /**
     * Finds and prints data about the {@link Technical} that has the shortest warranty period.
     * In case there is no technical exist among items, "No technical items." is printed.
     *
     * @param items entered items to print data for.
     */
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
