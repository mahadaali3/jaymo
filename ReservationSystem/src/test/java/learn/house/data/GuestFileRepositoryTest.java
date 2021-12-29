package learn.house.data;

import learn.house.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./Data/dont-wreck-my-house-data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./Data/dont-wreck-my-house-data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataException {
        List<Guest> all = repository.findAll();
        assertEquals(1000, all.size());
    }
    @Test
    void findbyEmail() throws DataException{
        Guest guest = repository.findByEmail("amountc@ehow.com");
        List<Guest> guests = new ArrayList<>();
        guests.add(guest);
        assertEquals(1,guests.size());
    }

}