package hr.java.production.utils;

import hr.java.production.model.Displayable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InputUtils {
    //true = Skip field inputs
    private static final boolean isMock = false;

    private InputUtils() {
    }

    public static String getStringInput(Scanner scanner, int i, String s) {
        if(isMock) {
            //Skip field inputs.
            //Input consists is a camil case class name with field name and index i. (e.g. categoryName_1)
            int spaceIndex = s.indexOf(' ');
            String formatted = s.replaceFirst(" ", "");
            formatted = formatted.substring(0, spaceIndex) + Character.toUpperCase(s.charAt(spaceIndex+1)) + s.substring(spaceIndex+2, s.indexOf(':'));

            return formatted + "_" + i;
        }
        return getStringInput(scanner, "Enter " + (i + 1) + ". " + s);
    }

    private static String getStringInput(Scanner scanner, String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input == null || input.isBlank()) {
            System.out.println("Wrong string input, please repeat...");
            return getStringInput(scanner, message);
        }
        return input;
    }

    public static BigDecimal getNumberInput(Scanner scanner, int i, String s) {
        if(isMock)
            return new BigDecimal(new Random().nextInt(20));
        return getNumberInput(scanner, "Enter " + (i + 1) + ". " + s);
    }

    private static BigDecimal getNumberInput(Scanner scanner, String message) {
        String value = getStringInput(scanner, message);
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            System.out.println("Wrong number input, must be number. Please repeat...");
            return getNumberInput(scanner, message);
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
    public static <T extends Displayable> T getListSelectionInput(Scanner scanner,
                                                                     int index,
                                                                     Class<?> clazz,
                                                                     List<T> list,
                                                                     boolean exitable) {
        index++; //Increase index for display (so 0. shows as 1.)
        String listClassName = list.get(0).getClass().getSimpleName().toLowerCase();
        String message = "Select " + index + ". " + clazz.getSimpleName().toLowerCase() +
                " " + listClassName + " (Enter number in front of " + listClassName + ")";
        if (exitable)
            message = message + ".. (for exit enter '-1')";
        System.out.println(message + ": ");
        for (int i = 0; i < list.size(); i++)
            System.out.println(i + ". " + list.get(i).toShortString());

        int intValue = -1;
        while (intValue > list.size() - 1 || intValue < 0) {
            if(isMock) {
                if(exitable)
                    intValue = new Random().nextInt(list.size() + 1) - 1; //Enable exit
                else
                    intValue = new Random().nextInt(list.size());
                System.out.println("Input: " + intValue);
                if(intValue == -1)
                    return null;
                continue;
            }
            intValue = getNumberInput(scanner, "Input: ").intValue();
            if (exitable && intValue == -1)
                return null;
            if (intValue > list.size() - 1 || intValue < 0)
                System.out.println("Wrong index input, please repeat...");
        }

        return list.get(intValue);
    }

    public static <T extends Displayable> List<T> getListSelectionInputs(Scanner scanner,
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