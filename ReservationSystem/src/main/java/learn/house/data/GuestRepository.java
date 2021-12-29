package learn.house.data;

import learn.house.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll() throws DataException;
    Guest findByEmail(String email) throws DataException;

    Guest findById(String id) throws DataException;
}
