package hr.tvz.ele.spec.oop.exception;

import hz.tvz.ele.spec.oop.moje.iznimke.DatumUPovijestiException;
import hz.tvz.ele.spec.oop.moje.iznimke.NegativnaTemperaturaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static final String FORMAT_DATUMA = "dd.MM.yyyy.";
    static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean ispravnaTemperatura;
        BigDecimal temperatura = null;
        do {
            try {
                temperatura = getTemperatura(scanner);
                ispravnaTemperatura = true;
            } catch (NegativnaTemperaturaException ex) {
                ispravnaTemperatura = false;
                System.out.println(ex.getMessage() + " Morate unijeti pozitivnu temperaturu.");
                log.error("Unesena neispravna vrijednost za temperaturu", ex);
            }
        } while (!ispravnaTemperatura);
        System.out.println("Unijeli ste temperaturu: " + temperatura);

        LocalDate datum = getLocalDate(scanner);
        System.out.println("Uneseni datum je: " + datum.getDayOfWeek());
    }

    private static BigDecimal getTemperatura(Scanner scanner) throws NegativnaTemperaturaException {
        BigDecimal temperatura = BigDecimal.ZERO;
        boolean ispravanUnos;
        do {
            System.out.print("Unesite temperaturu: ");

            try {
                temperatura = scanner.nextBigDecimal();
                ispravanUnos = true;
            } catch (InputMismatchException exception) {
                ispravanUnos = false;
                System.out.println("Neispravan unos, molimo unesite numeričku vrijednost.");
                log.error("Unesena neispravna vrijednost za temperaturu", exception);
            }
            scanner.nextLine();
        } while (!ispravanUnos);

        if(temperatura.compareTo(BigDecimal.ZERO) < 0)
            throw new NegativnaTemperaturaException("Unijeli ste negativnu temperaturu!");

        return temperatura;
    }

    private static LocalDate getLocalDate(Scanner scanner) {
        boolean ispravanUnos;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATUMA);
        LocalDate datum = LocalDate.now();
        do {
            System.out.print("Unesite datum mjerenja temperature (" + FORMAT_DATUMA + ") : ");
            String datumString = scanner.nextLine();

            try {
                datum = LocalDate.parse(datumString, formatter);
                ispravanUnos = true;
            }
            catch (DateTimeParseException ex) {
                ispravanUnos = false;
                System.out.println("Nijeli ste datum s neispravnim formatom! " +
                        "Molimo unesite datum ponovno.");
                log.error("Unesen je neispravan format datuma!", ex);
            }
        } while (!ispravanUnos);

        if(datum.isBefore(LocalDate.now().minusDays(1)))
            throw new DatumUPovijestiException("Unijeli ste datum u povijesti");

        return datum;
    }
}
