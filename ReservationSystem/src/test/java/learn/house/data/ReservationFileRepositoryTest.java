package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./Data/dont-wreck-my-house-data/reservations/3f413626-e129-4d06-b68c-36450822213f.csv";
    static final String TEST_FILE_PATH = "./Data/dont-wreck-my-house-data/reservations/3f413626-e129-4d06-b68c-36450822213f.csv";
    static final String TEST_DIR_PATH = "./Data/dont-wreck-my-house-data/reservations";

    final UUID hostId = UUID.fromString("3f413626-e129-4d06-b68c-36450822213f");
    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findReservationByHost() throws DataException {
        List<Reservation> reservations = repository.findReservationByHost(hostId);
        assertEquals(13, reservations.size());
    }

    @Test
    void addReservation() throws DataException {
        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);
        List<Reservation> all = repository.findReservationByHost(hostId);
        reservation.setId(13);
        reservation.setStartDate(LocalDate.of(2021, 5, 6));
        reservation.setEndDate(LocalDate.of(2021, 5, 7));
        guest.setId("400");
        reservation.setGuest(guest);
        reservation.setTotal(new BigDecimal("500"));

        all.add(reservation);
        reservation = repository.addReservation(reservation);


        assertEquals(16, all.size());
    }
}