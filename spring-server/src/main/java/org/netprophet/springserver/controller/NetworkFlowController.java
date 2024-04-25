package org.netprophet.springserver.controller;

import lombok.RequiredArgsConstructor;
import org.netprophet.springserver.dto.networkflow.NetworkFlowDto;
import org.netprophet.springserver.service.NetworkFlowService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
public class NetworkFlowController {

    private final NetworkFlowService networkFlowService;

    @GetMapping
    public List<NetworkFlowDto> findAll(Pageable requestDto) {
        return networkFlowService.findAll(requestDto);
    }
}
