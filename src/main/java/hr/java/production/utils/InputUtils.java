package hr.java.production.utils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InputUtils {
    //true = Skip field inputs
    private static final boolean isMock = true;
    private static final int DEFAULT_BOUNDS_MIN = 0;
    private static final int DEFAULT_BOUNDS_MAX = 100;

    private InputUtils() {
    }

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

    public static String getStringInput(Scanner scanner, String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input == null || input.isBlank()) {
            System.out.println("Wrong string input, please repeat...");
            return getStringInput(scanner, message);
        }
        return input;
    }

    public static BigDecimal getNumberInput(Scanner scanner, int i, String s) {
        return getNumberInput(scanner, i, s, DEFAULT_BOUNDS_MIN, DEFAULT_BOUNDS_MAX);
    }

    public static BigDecimal getNumberInput(Scanner scanner, int i, String s, int boundsMin, int boundsMax) {
        if(isMock) {
            final BigDecimal input = new BigDecimal(ThreadLocalRandom.current().nextInt(boundsMin, boundsMax + 1));
            System.out.println("Enter " + (i + 1) + ". " + s + input);
            return input;
        }
        return getNumberInput(scanner, "Enter " + (i + 1) + ". " + s, boundsMin, boundsMax);
    }

    private static BigDecimal getNumberInput(Scanner scanner, String message, int boundsMin, int boundsMax) {
        return getNumberInput(scanner, message, false, boundsMin, boundsMax);
    }

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
            System.out.println("Wrong number input, must be number. Please repeat...");
            return getNumberInput(scanner, message, exitable, boundsMin, boundsMax);
        }
    }

    //Bounds inclusive
    private static BigDecimal getForValidInput(Scanner scanner, String message, BigDecimal input,
                                               boolean exitable, int boundsMin, int boundsMax) {
        if (input.intValue() >= boundsMin && input.intValue() <= boundsMax)
            return input;
        else {
            if(exitable)
                System.out.println("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "] or -1 for exit. Please repeat...");
            else
                System.out.println("Number input must be in range [" +
                        boundsMin + ", " + boundsMax + "]. Please repeat...");
            return getNumberInput(scanner, message, exitable, boundsMin, boundsMax);
        }
    }

    /**
     * Selection of a single item from list based on number input.
     * If selection is not exitable user must provide at least one selection, and
     * cannot exit selection with -1. In opposite, if exitable is true user
     * can leave selection by providing -1 input and in this case return value is null.
     * Valid input is [0, list.size()-1].
     *
     * @return selected object from list param. If exitable and -1 return null.
     */
    public static <T> T getListSelectionInput(Scanner scanner,
                                              int index,
                                              Class<?> clazz,
                                              List<T> list,
                                              boolean exitable) {
        index++; //Increase index for display (so 0. index shows as 1.)
        String listClassName = list.get(0).getClass().getSimpleName().toLowerCase();
        String message = "Select " + index + ". " + clazz.getSimpleName().toLowerCase() +
                " " + listClassName + " (Enter number in front of " + listClassName + ")";
        if (exitable)
            message = message + ".. (for exit enter '-1')";
        return getListSelectionInput(scanner, message, list, exitable);
    }

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

    public static <T> List<T> getListSelectionInputs(Scanner scanner,
                                                     int index,
                                                     Class<?> clazz,
                                                     List<T> list) {
        List<T> listCopy = new ArrayList<>(list); //Copy list to preserve original list on removal
        List<T> input = new ArrayList<>();
        while (!listCopy.isEmpty()) {
            T selection = getListSelectionInput(scanner, index, clazz, listCopy, /*prevent exit if empty*/ !input.isEmpty());
            if (selection == null) {
                if (input.isEmpty())
                    System.out.println("Must provide at least one " + list.get(0).getClass().getSimpleName().toLowerCase() +
                            " for " + (index + 1) + ". " + clazz.getSimpleName().toLowerCase() + ".");
                else
                    break;
            } else
                input.add(selection);

            listCopy.removeAll(input); //Remove already selected items from the list
        }
        return input;
    }
}