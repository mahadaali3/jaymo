package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private GuestRepository repository;

    public GuestService(GuestRepository repository) {

        this.repository = repository;
    }

    public List<Guest> findAll() throws DataException {
        return repository.findAll();

    }

    public Guest findByEmail(String email) throws DataException {
        return repository.findByEmail(email);


    }

}
