package learn.house.data;

import learn.house.models.Host;

import java.util.List;
import java.util.UUID;

public interface HostRepository {
    Host findByEmail(String Email) throws DataException;

    List<Host> findAll() throws DataException;

    Host findByID(UUID id) throws DataException;
}
