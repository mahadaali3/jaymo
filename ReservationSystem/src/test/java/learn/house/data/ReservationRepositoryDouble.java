package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();
    final UUID hostId = UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db");

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        Host host = new Host();
        Guest guest = new Guest();
        host.setStandardRate(new BigDecimal("150"));
        host.setWeekendRate(new BigDecimal("150"));
        host.setId(hostId);
        reservation.setHost(host);
        reservation.setId(13);
        reservation.setStartDate(LocalDate.of(2021, 5, 6));
        reservation.setEndDate(LocalDate.of(2021, 5, 7));
        guest.setId("400");
        reservation.setGuest(guest);

        reservations.add(reservation);
    }

    public List<Reservation> findReservationByHost(UUID id) throws DataException {
        return reservations.stream()
                .filter(reservation -> reservation.getHost().getId().equals(hostId))
                .collect(Collectors.toList());
    }




    public Reservation addReservation(Reservation reservation) throws DataException {
        reservations.add(reservation);
        return reservation;
    }

    public boolean editReservation(Reservation reservation) throws DataException {
        return false;
    }

    public boolean cancelReservation(Reservation reservation) throws DataException {
        reservations.remove(reservation);
        return false;
    }

}
