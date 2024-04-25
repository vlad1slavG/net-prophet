package org.netprophet.springserver.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.netprophet.springserver.dto.networkflow.NetworkFlowDto;
import org.netprophet.springserver.repository.NetworkFlowRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NetworkFlowService {

    private final NetworkFlowRepository networkFlowRepository;
    private final ModelMapper modelMapper;

    public List<NetworkFlowDto> findAll(Pageable requestDto) {
        return networkFlowRepository.findAll(requestDto).stream()
                .map(e -> modelMapper.map(e, NetworkFlowDto.class))
                .toList();
    }
}
