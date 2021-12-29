package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;


import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    private final List<Guest> guests = new ArrayList<>();
    final static Guest guest = new Guest();


    public GuestRepositoryDouble() {
        guests.add(guest);
    }

    public List<Guest> findAll() throws DataException {
        return guests;
    }

    @Override
    public Guest findByEmail(String guestEmail) throws DataException {
        return guest;
    }

    public Guest findById(String guestId) throws DataException  {
        return guest;
    }
}