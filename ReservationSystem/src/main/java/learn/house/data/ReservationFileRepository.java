package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Reservation;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

@Repository
public class ReservationFileRepository implements ReservationRepository {
    private final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;



    public ReservationFileRepository(@Value("${reservationFilePath}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findReservationByHost(UUID id) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {
            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",");
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
            }catch(IOException e){
            throw new DataException("Could not open the file path: " + getFilePath(id), e);
        }
            return result;
    }


    public Reservation addReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findReservationByHost(reservation.getHost().getId());

        reservation.setId(getNextId(all));
        all.add(reservation);
        writeAll(all, reservation.getHost().getId());
        return reservation;
        }


    private String serialize(Reservation reservation) throws DataException {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getId(),
                reservation.getTotal());



    }




    private Reservation deserialize(String[] fields) {
        Reservation result = new Reservation();
        Guest guest = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        guest.setId((fields[3]));
        result.setGuest(guest);
        result.setTotal(new BigDecimal(fields[4]));

        return result;
    }

    private String getFilePath(UUID id) {

        return Paths.get(directory, id + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, UUID id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(id))) {

            writer.println(HEADER);

            if (reservations == null) {
                return;
            }

            for (Reservation r : reservations) {
                writer.println(serialize(r));
            }
        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }


    private int getNextId(List<Reservation> all) {
        int nextId = 0;
        for (Reservation e : all) {
            nextId = Math.max(nextId, e.getId());
        }
        return nextId + 1;
    }

}
