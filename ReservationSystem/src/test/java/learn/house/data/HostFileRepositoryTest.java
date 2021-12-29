package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
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

class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./Data/dont-wreck-my-house-data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./Data/dont-wreck-my-house-data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataException {
        List<Host> all = repository.findAll();
        assertEquals(1000, all.size());
    }
    @Test
    void findbyEmail() throws DataException{
        Host host = repository.findByEmail("krhodes1@posterous.com");
        List<Host> hosts = new ArrayList<>();
        hosts.add(host);
        assertEquals(1,hosts.size());
    }
}