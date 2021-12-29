package learn.house.data;


import learn.house.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class HostFileRepository implements HostRepository {
    private final String filePath;

    public HostFileRepository(@Value("${hostFilePath}") String filePath) {
        this.filePath = filePath;
    }


    public List<Host> findAll() throws DataException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Host findByID(UUID id) throws DataException {
        return findAll().stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }



    @Override
    public Host findByEmail(String Email) throws DataException {
        return findAll().stream()
                .filter(host -> host.getEmail().equals(Email))
                .findFirst()
                .orElse(null);
    }


    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(UUID.fromString(fields[0]));
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhoneNumber(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(Integer.parseInt(fields[7]));
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;

    }

}
