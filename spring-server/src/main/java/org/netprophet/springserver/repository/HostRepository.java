package org.netprophet.springserver.repository;

import org.netprophet.springserver.model.Host;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface HostRepository extends Repository<Host, String> {
    void save(Host host);
    List<Host> findAll(Pageable pageable);
    Optional<Host> findHostByHostIp(String hostIp);
    int countAllByLastActiveDateTimeAfter(Instant time);
}
