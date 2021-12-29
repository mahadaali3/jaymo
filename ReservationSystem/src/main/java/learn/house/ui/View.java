package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class View {

    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in MM/dd/yyyy format.";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private final Scanner console = new Scanner(System.in);

    public void println(String message) {
        System.out.println(message);
    }

    public MenuOption displayMenuGetOption() {
        ;
        System.out.println("Main Menu");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getOption());
        }

        String msg = String.format("Select [%s-%s]:", 1, options.length);
        int value = readInt(msg, 1, options.length);
        return options[value - 1];
    }

    private int readInt(String message, int min, int max) {
        int result;
        do {
            result = readInt(message);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);
        return result;
    }

    private int readInt(String message) {
        String input = null;
        int result = 0;
        boolean isValid = false;
        do {
            try {
                input = readRequiredString(message);
                result = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isValid);

        return result;
    }

    private String readRequiredString(String message) {
        String result;
        do {
            result = readString(message);
            if (result.trim().length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.trim().length() == 0);
        return result;
    }

    private String readString(String message) {
        System.out.print(message);
        return console.nextLine();
    }

    public void displayHeader(String message) {
        System.out.println("");
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public String enterGuestEmail() {
        System.out.println("Guest Email: ");
        String guestEmail = console.next();
        return guestEmail;
    }

    public String enterHostEmail() {
        System.out.print("Host Email: ");
        String hostEmail = console.next();
        return hostEmail;
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public void showReservation(List<Reservation> reservations, Host host) {

        if (reservations == null || reservations.size() == 0) {
            System.out.println("Host could not be found");
        } else {
            System.out.printf("%s: %s, %s%n", host.getLastName(), host.getCity(), host.getState());

            for (Reservation reservation : reservations.stream().sorted((a, b) -> a.getStartDate().compareTo(b.getStartDate())).collect(Collectors.toList())) {

                System.out.printf("ID: %s, %s - %s, Guest: %s, %s, Email: " +
                                "%s, Total: %s%n", reservation.getId(), reservation.getStartDate(), reservation.getEndDate(),
                        reservation.getGuest().getLastName(), reservation.getGuest().getFirstName(),
                        reservation.getGuest().getEmail(), reservation.getTotal());


            }
        }

    }

    public LocalDate promptStartDate() {
        System.out.println("Reservation Start Date: ");
        LocalDate startDate = LocalDate.parse(console.next());
        return startDate;
    }

    public LocalDate promptEndDate() {
        System.out.println("Reservation End Date: ");
        LocalDate endDate = LocalDate.parse(console.next());
        return endDate;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public Host getHost(List<Host> allHosts) throws DataException {
        Scanner console = new Scanner(System.in);

        Host toReturn = null;
        do {
            System.out.print("Host Email: ");
            String email = console.nextLine();
            toReturn = allHosts.stream().filter(h -> h.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);

            if (toReturn == null) {
                System.out.println("No host could be found");
            }


        } while (toReturn == null);

        return toReturn;
    }

    public Guest getGuest(List<Guest> allGuests) throws DataException {
        Scanner console = new Scanner(System.in);
        Guest toReturn = null;

        do {
            System.out.print("Guest Email: ");
            String email = console.next();
            toReturn = allGuests.stream().filter(h -> h.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
            if (toReturn == null) {
                System.out.println("No guest could be found");
            }


        } while (toReturn == null);

        return toReturn;
    }

    public Reservation createReservation(Host host, Guest guest) throws DataException {
        String input = "";
        Scanner console = new Scanner(System.in);
        LocalDate startDate;
        LocalDate endDate;
        LocalDate today = LocalDate.now();

        do {
            startDate = readLocalDate("Start (MM/dd/yyyy): ");
            endDate = readLocalDate("End (MM/dd/yyyy): ");

            if (today.isAfter(startDate)) {
                System.out.println("Reservations must be made for a future date! Please try again.");
            } else if (startDate.isAfter(endDate)) {
                System.out.println("Reservation start date must come before the end date! Please try again.");
            }

        } while (startDate.isAfter(endDate) || today.isAfter(startDate));

        Reservation reservation = new Reservation(host, startDate, endDate, guest);
        return reservation;


    }

    public boolean confirmReservation(Reservation reservation) {

        displayHeader("Summary");
        System.out.println("Start: " + reservation.getStartDate());
        System.out.println("End: " + reservation.getEndDate());
        System.out.println("Total: " + reservation.getTotal());
        System.out.println("Is this okay? [y/n]: ");
        String input = console.nextLine();
        return input.equalsIgnoreCase("y");


    }
    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }
    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            displayMessage(message);
        }

    }
}
