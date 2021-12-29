package learn.house.data;

import learn.house.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {
    private final String filePath;
    private final String delimiter = ",";

    public GuestFileRepository(@Value("${guestFilePath}") String filePath) {
        this.filePath = filePath;
    }


    public List<Guest> findAll() throws DataException {
        List<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(delimiter);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException e) {
            throw new DataException("Could not open file at: " + filePath, e);
        }
        return result;
    }
    @Override
    public Guest findById(String id) throws DataException {
       return findAll().stream()
               .filter(guest -> guest.getId().equals(id))
               .findFirst()
               .orElse(null);
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(fields[0]);
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;

    }

}
