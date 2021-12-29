package learn.house.data;

import learn.house.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HostRepositoryDouble implements HostRepository{

    private final List<Host> hosts = new ArrayList<>();
    final static Host host = makeHost();

    public HostRepositoryDouble() {
        hosts.add(host);
    }

    public List<Host> findAll() throws DataException {
        return hosts;
    }

    @Override
    public Host findByID(UUID id) throws DataException {
        return hosts.stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }



    @Override
    public Host findByEmail(String hostEmail) throws DataException {
        return findAll().stream()
                .filter(host -> host.getEmail().equals(hostEmail))
                .findFirst()
                .orElse(null);
    }

    private static Host makeHost() {
        Host host = new Host();
        host.setId(UUID.fromString("d9fe6bb5-203a-4817-9de0-486e825270e8"));
        host.setStandardRate(new BigDecimal("200"));
        host.setWeekendRate(new BigDecimal("250"));
        return host;
    }
}