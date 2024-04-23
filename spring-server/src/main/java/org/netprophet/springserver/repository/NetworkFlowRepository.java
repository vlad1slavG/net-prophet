package org.netprophet.springserver.repository;

import org.netprophet.springserver.model.NetworkFlow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface NetworkFlowRepository extends Repository<NetworkFlow, String> {
    void saveAll(Iterable<NetworkFlow> networkFlows);
    List<NetworkFlow> findAll(Pageable pageable);
}
