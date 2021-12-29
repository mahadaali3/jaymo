package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;


    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }


    public void run() throws DataException {
        boolean isRunning = true;
        while (isRunning) {
            try {
                runMenu();
            } catch (DataException e) {
                view.displayMessage("Host has no reservations!");
            }
        }

    }

    public void runMenu() throws DataException {
        MenuOption option;

        do {
            option = view.displayMenuGetOption();
            switch (option) {
                case EXIT:
                    break;
                case VIEW:
                    findReservationByHost();
                    break;
                case MAKE:
                    makeReservation();
                    break;
                case EDIT:

                    break;
                case CANCEL:

                    break;


            }

        } while (option != MenuOption.EXIT);
    }

    public void findReservationByHost() throws DataException {
        view.displayHeader("View Reservations for Host");
        Host host = view.getHost(hostService.findAll());
        if (host.getId() != null) {
            List<Reservation> reservations = reservationService.findReservationsByHost(host.getId());
            view.showReservation(reservations, host);
        } else {
            view.displayMessage("No host could be found");
        }


    }


    public void makeReservation() throws DataException {
        view.displayHeader("Make a Reservation");

        Guest guest = view.getGuest(guestService.findAll());
        Host host = view.getHost(hostService.findAll());
        List<Reservation> reservations = reservationService.findReservationsByHost(host.getId());
        view.showReservation(reservations, host);


        Reservation reservation = view.createReservation(host, guest);

        BigDecimal total = reservationService.addUpTotal(reservation);
        reservation.setTotal(total);

        if (view.confirmReservation(reservation)) {
            Result result = reservationService.makeReservation(reservation);

            if (!result.isSuccess()) {
                view.displayStatus(false, result.getMessages());
            } else {
                String successMessage = String.format("Reservation %s created.", result.getReservation().getId());
                view.displayStatus(true, successMessage);
            }

        }


    }


}


