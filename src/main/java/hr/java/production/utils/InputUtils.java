package hr.java.production.utils;

import hr.java.production.exception.MultipleInstanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Helper class that handles inputs.
 */
public class InputUtils {
    //true = Skip field inputs
    private static final boolean isMock = true;
    private static final int DEFAULT_BOUNDS_MIN = 0;
    private static final int DEFAULT_BOUNDS_MAX = 100;

    //true if remove item from list on select from list (to prevent multiple inputs)
    //From Lab 3 - When was needed to throw exception on multiple instances input
    public static final boolean LIST_INPUT_REMOVAL = false;

    private static final Logger logger = LoggerFactory.getLogger(InputUtils.class);

    /**
     * Empty constructor preventing instantiating this object.
     */
    private InputUtils() {
    }

    /**
     * Constructs message for getting string value input and calls real method for getting string input.
     *
     * @param scanner allows inputs.
     * @param i index of item in the input list.
     * @param s message string suffix.
     * @return entered string value if entry was valid.
     */
    public static String getStringInput(Scanner scanner, int i, String s) {
        if(isMock) {
            //Skip field inputs.
            //Input consists is a camil case class name with field name and index i. (e.g. categoryName_1)
            int spaceIndex = s.indexOf(' ');
            String formatted = s.replaceFirst(" ", "");
            formatted = formatted.substring(0, spaceIndex) +
                    Character.toUpperCase(s.charAt(spaceIndex+1)) + s.substring(spaceIndex+2, s.indexOf(':'));
            final String input = formatted + "_" + i;
            System.out.println("Enter " + (i + 1) + ". " + s + input);
            return input;
        }
        return getStringInput(scanner, "Enter " + (i + 1) + ". " + s);
    }

    /**
     * Gets string input value if not null or empty, otherwise display error and repeat entry.
     *
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @return entered string value if entry was valid.
     */
    public static String getStringInput(Scanner scanner, String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input == null || input.isBlank()) {
            logger.error("Wrong string input.. Entered value: " + input, new InputMismatchException());
            System.out.println("[ERROR] Wrong string input, please repeat...");
            return getStringInput(scanner, message);
        }
        return input;
    }

    /**
     * Calls method for constructing number input message with default bounds.
     *
     * @param scanner allows inputs.
     * @param i index of item in the input list.
     * @param s message string suffix.
     * @return entered number input if entry was valid.
     */
    public static BigDecimal getNumberInput(Scanner scanner, int i, String s) {
        return getNumberInput(scanner, i, s, DEFAULT_BOUNDS_MIN, DEFAULT_BOUNDS_MAX);
    }

    /**
     * Constructs number input message for getting number value within provided bounds.
     *
     * @param scanner allows inputs.
     * @param i index of item in the input list.
     * @param s message string suffix.
     * @param boundsMin minimum number value allowed, inclusive.
     * @param boundsMax maximum number value allowed, inclusive.
     * @return entered number input if entry was valid.
     */
    public static BigDecimal getNumberInput(Scanner scanner, int i, String s, int boundsMin, int boundsMax) {
        if(isMock) {
            final BigDecimal input = new BigDecimal(ThreadLocalRandom.current().nextInt(boundsMin, boundsMax + 1));
            System.out.println("Enter " + (i + 1) + ". " + s + input);
            return input;
        }
        return getNumberInput(scanner, "Enter " + (i + 1) + ". " + s, boundsMin, boundsMax);
    }

    /**
     * Calls method for getting entered number value within provided bounds without possibility
     * to stop inputs with '-1' entry.
     *
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @param boundsMin minimum number value allowed, inclusive.
     * @param boundsMax maximum number value allowed, inclusive.
     * @return entered number input if entry was valid.
     */
    private static BigDecimal getNumberInput(Scanner scanner, String message, int boundsMin, int boundsMax) {
        return getNumberInput(scanner, message, false, boundsMin, boundsMax);
    }

    /**
     * Gets number input value if valid and within bounds, otherwise display error and repeat entry.
     *
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param boundsMin minimum number value allowed, inclusive.
     * @param boundsMax maximum number value allowed, inclusive.
     * @return entered number input if entry was valid.
     */
    private static BigDecimal getNumberInput(Scanner scanner, String message, boolean exitable, int boundsMin, int boundsMax) {
        String value = getStringInput(scanner, message);
        try {
            BigDecimal input = new BigDecimal(value);
            if(exitable) {
                if(input.intValue() == -1)
                    return input;
                else
                    return getForValidInput(scanner, message, input, true, boundsMin, boundsMax);
            }
            else
                return getForValidInput(scanner, message, input, false, boundsMin, boundsMax);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Wrong number input, must be number. Please repeat...");
            return getNumberInput(scanner, message, exitable, boundsMin, boundsMax);
        }
    }

    /**
     * Checks whether input value is within bounds (inclusive) and return its value if it is valid.
     *
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @param input entered number value to be checked.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param boundsMin minimum number value allowed, inclusive.
     * @param boundsMax maximum number value allowed, inclusive.
     * @return input value if input is valid, otherwise display error and repeat entry.
     */
    private static BigDecimal getForValidInput(Scanner scanner, String message, BigDecimal input,
                                               boolean exitable, int boundsMin, int boundsMax) {
        if (input.intValue() >= boundsMin && input.intValue() <= boundsMax)
            return input;
        else {
            if(exitable) {
                logger.error("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "] or -1 for exit. Entered value: " + input.intValue(), new InputMismatchException());
                System.out.println("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "] or -1 for exit. Please repeat...");
            }
            else {
                logger.error("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "]. Entered value: " + input.intValue(), new InputMismatchException());
                System.out.println("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "]. Please repeat...");
            }
            return getNumberInput(scanner, message, exitable, boundsMin, boundsMax);
        }
    }

    /**
     * Gets object from list based on entered index.
     *
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @param list original list containing all items.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param <T> type of object to be returned if everything is ok.
     * @return selected object from list param. If exitable and -1 return null.
     */
    public static <T> T getListSelectionInput(Scanner scanner, String message, List<T> list, boolean exitable) {
        System.out.println(message + ": ");
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + ". " + list.get(i).toString());

        final Random random = new Random();
        int intValue;
        if(isMock) {
            if(exitable) {
                intValue = random.nextInt(list.size()) + 1; //Enable exit
                if(random.nextBoolean())
                    intValue = -1;
            }
            else
                intValue = random.nextInt(list.size()) + 1;
            System.out.println("Input: " + intValue);
            if(intValue == -1)
                return null;
            else
                return list.get(intValue-1);
        }
        intValue = getNumberInput(scanner, "Input: ", true, 1, list.size()).intValue();
        if(intValue == -1)
            return null;
        return list.get(intValue-1);
    }

    /**
     * Gets list of selected items from 'list' param.
     *
     * @param scanner allows inputs.
     * @param index index of item in the input list.
     * @param clazz class of the input items.
     * @param list original list containing all items that user can choose of.
     * @param <T> type of object to be returned if everything is ok.
     * @return list of selected items.
     */
    public static <T> List<T> getListSelectionInputs(Scanner scanner,
                                                     int index,
                                                     Class<?> clazz,
                                                     List<T> list) {
        List<T> listCopy = new ArrayList<>(list); //Copy list to preserve original list on removal
        List<T> input = new ArrayList<>();
        while (!listCopy.isEmpty()) {
            T selection = getListSelectionInput(scanner, index, clazz, listCopy, /*prevent exit if empty*/ !input.isEmpty(), input);
            if (selection == null) {
                //TODO - Fix with wrong "x. factory LAPTOP!??!?!?!"
                if (input.isEmpty()) {
                    logger.error("Wrong list selection. Must provide ast least one input value", new InputMismatchException());
                    System.out.println("[ERROR] Must provide at least one " + list.get(0).getClass().getSimpleName().toLowerCase() +
                            " for " + (index + 1) + ". " + clazz.getSimpleName().toLowerCase() + ".");
                }
                else
                    break;
            } else
                input.add(selection);

            if(LIST_INPUT_REMOVAL)
                listCopy.removeAll(input); //Remove already selected items from the list
        }
        return input;
    }

    /**
     * Selection of a single item from list based on number input.
     * If selection is not exitable user must provide at least one selection, and
     * cannot exit selection with -1. In opposite, if exitable is true user
     * can leave selection by providing -1 input and in this case return value is null.
     * Valid input is [0, list.size()-1].
     *
     * @param scanner allows inputs.
     * @param index index of item in the input list.
     * @param clazz class of the input items.
     * @param list original list containing all items that user can choose of.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param previousInputs list of previously selected items.
     * @param <T> type of object to be returned if everything is ok.
     * @return selected object from list param. If exitable and -1 return null.
     */
    private static <T> T getListSelectionInput(Scanner scanner,
                                              int index,
                                              Class<?> clazz,
                                              List<T> list,
                                              boolean exitable,
                                              List<T> previousInputs) {
        index++; //Increase index for display (so 0. index shows as 1.)
        String listClassName = list.get(0).getClass().getSimpleName().toLowerCase();
        String message = "Select " + index + ". " + clazz.getSimpleName().toLowerCase() +
                " " + listClassName + " (Enter number in front of " + listClassName + ")";
        if (exitable)
            message = message + ".. (for exit enter '-1')";

        T value;
        try {
            value = getListSelectionInput(scanner, message, list, exitable, previousInputs);
        } catch (MultipleInstanceException e) {
            logger.error(e.getMessage(), e);
            System.out.println("[ERROR] " + e.getMessage() + ". Please repeat with unique input..");
            return getListSelectionInput(scanner, index, clazz, list, exitable, previousInputs);
        }

        return value;
    }


    /**
     * Calls selection from list but without previous selections (empty list) which allows multiple same items.
     *
     * @param scanner allows inputs.
     * @param index index of item in the input list.
     * @param clazz class of the input items.
     * @param list original list containing all items that user can choose of.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param <T> type of object to be returned if everything is ok.
     * @return selected object from list param. If exitable and -1 return null.
     */
    public static <T> T getListSelectionInput(Scanner scanner,
                                              int index,
                                              Class<?> clazz,
                                              List<T> list,
                                              boolean exitable) {
        return getListSelectionInput(scanner, index, clazz, list, exitable, new ArrayList<>());
    }

    /**
     * @param scanner allows inputs.
     * @param message message to display before input.
     * @param list original list containing all items that user can choose of.
     * @param exitable true if user can stop inputs with entering '-1'.
     * @param previousInputs list of previously selected items
     * @param <T> type of object to be returned if everything is ok.
     * @return selected object from list param if valid, otherwise throw exception. If exitable and -1 return null.
     * @throws MultipleInstanceException in case selected value is present in previousInputs.
     */
    private static <T> T getListSelectionInput(Scanner scanner, String message, List<T> list,
                                              boolean exitable, List<T> previousInputs) throws MultipleInstanceException {
        System.out.println(message + ": ");
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + ". " + list.get(i).toString());

        final Random random = new Random();
        int intValue;
        if(isMock) {
            if(exitable) {
                intValue = random.nextInt(list.size()) + 1; //Enable exit
                if(random.nextBoolean())
                    intValue = -1;
            }
            else
                intValue = random.nextInt(list.size()) + 1;
            System.out.println("Input: " + intValue);
            if(intValue == -1)
                return null;
            else
                return getValue(list, previousInputs, intValue);
        }
        intValue = getNumberInput(scanner, "Input: ", true, 1, list.size()).intValue();
        if(intValue == -1)
            return null;
        return getValue(list, previousInputs, intValue);
    }

    /**
     * Gets value that was selected from list, if that values was not selected before for same item.
     * In case value already exists and list input removal is disabled, throw exception.
     *
     * @param list original list containing all items.
     * @param previousInputs list of previously selected items.
     * @param intValue entered index for that item (index in front of the item in list).
     * @param <T> type of object to be returned if everything is ok.
     * @return object to be returned if everything is ok.
     * @throws MultipleInstanceException if list removal is disabled and selected value already exists in previousInputs.
     */
    private static <T> T getValue(List<T> list, List<T> previousInputs, int intValue) throws MultipleInstanceException {
        T value = list.get(intValue - 1);
        if (previousInputs.contains(value) && !LIST_INPUT_REMOVAL)
            throw new MultipleInstanceException("Input was already selected before");
        return value;
    }
}