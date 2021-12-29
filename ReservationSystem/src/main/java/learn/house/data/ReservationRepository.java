package learn.house.data;

import learn.house.models.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
    List<Reservation> findReservationByHost(UUID id) throws DataException;
    Reservation addReservation(Reservation reservation) throws DataException;
}
