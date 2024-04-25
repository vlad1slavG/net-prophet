package org.netprophet.springserver.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.netprophet.springserver.dto.host.HostDto;
import org.netprophet.springserver.repository.HostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final ModelMapper modelMapper;

    public List<HostDto> findAllPageable(Pageable pageable) {
        return hostRepository.findAll(pageable).stream()
                .map(e -> modelMapper.map(e, HostDto.class))
                .toList();
    }

    public int getActiveHosts() {
        return hostRepository.countAllByLastActiveDateTimeAfter(Instant.now().minusSeconds(40));
    }
}
