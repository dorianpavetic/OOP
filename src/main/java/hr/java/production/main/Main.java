package hr.java.production.main;

import hr.java.production.enums.City;
import hr.java.production.enums.StatusZadatka;
import hr.java.production.exception.MultipleCategoryNamesException;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import hr.java.production.utils.InputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static hr.java.production.utils.InputUtils.getForValidInput;
import static hr.java.production.utils.InputUtils.getStringInput;

/**
 * Runs app and contains main operations about app.
 */
public class Main {
    public static final String CURRENCY_UNIT = "HRK";
    private static final int NUM_CATEGORY_INPUTS = 3;
    private static final int NUM_ITEM_INPUTS = 5;
    private static final int NUM_FACTORY_INPUTS = 2;
    private static final int NUM_STORE_INPUTS = 2;

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
        Set<Item> items = getItemInputs(scanner, Arrays.asList(categories));

        List<Zadatak> tasks = new ArrayList<>();
        int selection = 0;
        while(selection != -1) {
            System.out.println("Odaberite akciju:");
            System.out.println("\t1. Kreiraj zadatak");
            System.out.println("\t2. Otkaži zadatak");
            System.out.println("\t3. Ispis statitstike zadataka prema statusima");
            System.out.println("\t-1. Završi program");
            System.out.print("Odabir: ");

            selection = scanner.nextInt();

            switch (selection) {
                case 1:
                    Zadatak zadatak = createTask(scanner, tasks, items);
                    tasks.add(zadatak);
                    System.out.println(tasks);
                    break;
                case 2:
                    cancelTask(scanner, tasks);
                    System.out.println(tasks);
                    break;
                case 3:
                    printStatistics(tasks);
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Pogrešan unos, molimo ponovite..");
            }
        }


        /*Category[] categories = getCategoryInputs(scanner);
        Set<Item> items = getItemInputs(scanner, Arrays.asList(categories));
        Factory[] factories = getFactoryInputs(scanner, items);
        Store[] stores = getStoreInputs(scanner, items);

        System.out.println();
        findLargestItemFactories(items, Arrays.asList(factories));
        System.out.println();
        findCheapestItemStores(items, Arrays.asList(stores));
        System.out.println();
        printEdiblesInfo(items);
        System.out.println();
        printShortestWarrantyDurationTechnical(items);

        System.out.println();
        printAndSortItemsByCategories(items);
        System.out.println();
        printCheapestAndMostExpensiveEdibleTechnical(items); */
    }

    private static void printStatistics(List<Zadatak> tasks) {
        System.out.println("Statistika zadataka (ukupno " + tasks.size() + "): ");
        long uIzradiCount = tasks.stream()
                .filter(zadatak1 -> zadatak1.getStatusZadatka() == StatusZadatka.U_IZRADI).count();
        long zaIzraduCount = tasks.stream()
                .filter(zadatak1 -> zadatak1.getStatusZadatka() == StatusZadatka.ZA_IZRADU).count();
        long izradjenoCount = tasks.stream()
                .filter(zadatak1 -> zadatak1.getStatusZadatka() == StatusZadatka.IZRADJENO).count();
            System.out.println("\t- ZA_IZRADU: " + zaIzraduCount);
            System.out.println("\t- U_IZRADI: " + uIzradiCount);
            System.out.println("\t- IZRADJENO: " + izradjenoCount);
    }

    private static void cancelTask(Scanner scanner, List<Zadatak> tasks) {
        Zadatak zadatak = InputUtils.getListSelectionInput(scanner, 1, Zadatak.class, tasks, false);
        zadatak.setStatusZadatka(StatusZadatka.IZRADJENO);
    }

    private static Zadatak createTask(Scanner scanner, List<Zadatak> tasks, Set<Item> items) {
        long brojZadatka = InputUtils.getNumberInput(scanner, tasks.size(), "broj zadatka").longValue();
        Set<Item> selectedItems = new HashSet<>(InputUtils.getListSelectionInputs(scanner,
                tasks.size()-1, Zadatak.class, items.stream().toList()));

        scanner.nextLine();
        LocalDateTime localDateTime = null;
        while (localDateTime == null) {
            try {
                //System.out.print("Unesite datum i vrijeme pocetka za " + tasks.size() + ". zadatak: ");
                String localDateString = InputUtils.getStringInput(scanner, "Unesite pocetak zadatka: ");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                formatter = formatter.withLocale(Locale.getDefault());
                localDateTime = LocalDateTime.parse(localDateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Pogresan pocetak, molimo ponovite..");
            }
        }

        Zadatak zadatak = new Zadatak(brojZadatka, localDateTime, selectedItems);
        if(zadatak.getDatumPocetkaIzrade().isBefore(zadatak.getDatumKreiranja()))
            zadatak.setStatusZadatka(StatusZadatka.U_IZRADI);
        return zadatak;
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
            String description = getStringInput(scanner, i, "category description: ");
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

        String categoryName = getStringInput(scanner, i, "category name: ");
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
    private static Set<Item> getItemInputs(Scanner scanner, List<Category> categories) {
        Set<Item> items = new HashSet<>();
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

            String name = getStringInput(scanner, i, "item name: ");
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
                System.out.println("Entered food item has " + edible.calculateKilocalories() + " kilokalorija.");
                System.out.println("Total price of this food item is " + edible.calculatePrice() + " " + CURRENCY_UNIT);
            }

            items.add(item);
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
    private static Factory[] getFactoryInputs(Scanner scanner, Set<Item> items) {
        Factory[] factories = new Factory[NUM_FACTORY_INPUTS];
        for(int i = 0; i < NUM_FACTORY_INPUTS; i++) {
            String name = getStringInput(scanner, i, "factory name: ");
            Address address = getAddressInput(scanner, i, Factory.class);
            Set<Item> selectedItems = new HashSet<>(InputUtils.getListSelectionInputs(scanner, i, Factory.class, items.stream().toList()));
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
        String street = getStringInput(scanner, i, addressFor + " address street: ");
        String houseNumber = getStringInput(scanner, i, addressFor + " house number: ");
        City city = InputUtils.getListSelectionInput(scanner, i, City.class, Arrays.asList(City.values()), false);
        return new Address.Builder()
                .street(street)
                .houseNumber(houseNumber)
                .city(city)
                .build();
    }

    /**
     * Gets inputs about stores and return them as array.
     *
     * @param scanner allows inputs.
     * @param items entered items which can be assigned to stores.
     * @return array of entered stores.
     */
    private static Store[] getStoreInputs(Scanner scanner, Set<Item> items) {
        Store[] stores = new Store[NUM_STORE_INPUTS];
        for(int i = 0; i < NUM_STORE_INPUTS; i++) {
            String name = getStringInput(scanner, i, "store name: ");
            String webAddress = getStringInput(scanner, i, "store web address: ");
            Set<Item> selectedItems = new HashSet<>(InputUtils.getListSelectionInputs(scanner, i, Store.class, items.stream().toList()));
            stores[i] = new Store(name, webAddress, selectedItems);
        }
        return stores;
    }

    /**
     * Finds and prints the list of factories that produce item with the largest volume.
     *  @param items entered items to find the largest volume item.
     * @param factories entered factories to filter for the largest volume item.
     */
    private static void findLargestItemFactories(Set<Item> items, List<Factory> factories) {
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
                        .filter(factory -> factory.getItems().contains(largestVolumeItem))
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
     *  @param items entered items to find the cheapest item.
     * @param stores entered stores to filter for the cheapest item.
     */
    private static void findCheapestItemStores(Set<Item> items, List<Store> stores) {
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
                        .filter(factory -> factory.getItems().contains(cheapestItem))
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
    private static void printEdiblesInfo(Set<Item> items) {
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

    /**
     * Finds and prints data about the {@link Technical} that has the shortest warranty period.
     * In case there is no technical exist among items, "No technical items." is printed.
     *
     * @param items entered items to print data for.
     */
    private static void printShortestWarrantyDurationTechnical(Set<Item> items) {
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

    private static void printAndSortItemsByCategories(Set<Item> items) {
        Map<Category, Set<Item>> itemsByCategoriesMap = new HashMap<>();
        for(Item item : items) {
            if(itemsByCategoriesMap.containsKey(item.getCategory())) {
                Set<Item> itemsByCategory = itemsByCategoriesMap.get(item.getCategory());
                itemsByCategory.add(item);
            }
            else {
                Set<Item> itemsByCategory = new HashSet<>();
                itemsByCategory.add(item);
                itemsByCategoriesMap.put(item.getCategory(), itemsByCategory);
            }
        }

        for(Category category : itemsByCategoriesMap.keySet()) {
            System.out.println("Category " + category.getName());
            Item cheapestItem = itemsByCategoriesMap
                    .get(category)
                    .stream()
                    .sorted(new ProductionSorter(true))
                    .findFirst()
                    .orElse(null);
            if(cheapestItem != null)
                System.out.println("\t- The cheapest item for this category is: " +
                        cheapestItem.getName() + ", Price: " + cheapestItem.getSellingPrice());
            else
                System.out.println("\t- Can't find cheapest item for this category");

            Item mostExpensiveItem = itemsByCategoriesMap
                    .get(category)
                    .stream()
                    .sorted(new ProductionSorter(false))
                    .findFirst()
                    .orElse(null);
            if(mostExpensiveItem != null)
                System.out.println("\t- The most expensive item for this category is: " +
                        mostExpensiveItem.getName() + ", Price: " + mostExpensiveItem.getSellingPrice());
            else
                System.out.println("\t- Can't find cheapest item for this category");
        }
    }

    private static void printCheapestAndMostExpensiveEdibleTechnical(Set<Item> items) {
        Set<Item> edibleOrTechnicalItems = items.stream()
                .filter(item -> item instanceof Edible || item instanceof Technical)
                .collect(Collectors.toSet());

        Item cheapestItem = edibleOrTechnicalItems
                .stream()
                .sorted(new ProductionSorter(true))
                .findFirst()
                .orElse(null);
        if(cheapestItem != null)
            System.out.println("The cheapest item is: " + cheapestItem.getName() + " (" +
                    cheapestItem.getClass().getSimpleName() + "), Price: " + cheapestItem.getSellingPrice());
        else
            System.out.println("Can't find cheapest item.");

        Item mostExpensiveItem = edibleOrTechnicalItems
                .stream()
                .sorted(new ProductionSorter(false))
                .findFirst()
                .orElse(null);
        if(mostExpensiveItem != null)
            System.out.println("The most expensive item is: " + mostExpensiveItem.getName() + " (" +
                    mostExpensiveItem.getClass().getSimpleName() + "), Price: " + mostExpensiveItem.getSellingPrice());
        else
            System.out.println("Can't find cheapest item.");
    }
}
