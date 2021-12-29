package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.data.HostRepositoryDouble;
import learn.house.data.ReservationRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(), new GuestRepositoryDouble(), new HostRepositoryDouble());
    final UUID hostId = UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db");

    @Test
    void addReservationShouldAdd() throws DataException {
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

        Result result = service.makeReservation(reservation);
        assertTrue(result.isSuccess());
    }

    


    @Test
    void addReservationShouldNotAddIfGuestMissing() throws DataException {
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
        reservation.setGuest(guest);
        Result result = service.makeReservation(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void addReservationShouldNotAddIfStartDateMissing() throws DataException {
        Reservation reservation = new Reservation();
        Host host = new Host();
        Guest guest = new Guest();
        host.setStandardRate(new BigDecimal("150"));
        host.setWeekendRate(new BigDecimal("150"));
        host.setId(hostId);
        reservation.setHost(host);
        reservation.setId(13);
        reservation.setEndDate(LocalDate.of(2021, 5, 7));
        guest.setId("400");
        reservation.setGuest(guest);

        Result result = service.makeReservation(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void addReservationShouldNotAddIfEndDateMissing() throws DataException {
        Reservation reservation = new Reservation();
        Host host = new Host();
        Guest guest = new Guest();
        host.setStandardRate(new BigDecimal("150"));
        host.setWeekendRate(new BigDecimal("150"));
        host.setId(hostId);
        reservation.setHost(host);
        reservation.setId(13);
        reservation.setStartDate(LocalDate.of(2021, 5, 6));
        guest.setId("400");
        reservation.setGuest(guest);

        Result result = service.makeReservation(reservation);
        assertFalse(result.isSuccess());
    }

}