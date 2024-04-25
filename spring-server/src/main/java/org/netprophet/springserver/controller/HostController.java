package org.netprophet.springserver.controller;

import lombok.RequiredArgsConstructor;
import org.netprophet.springserver.dto.host.HostDto;
import org.netprophet.springserver.service.HostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping
    public List<HostDto> findAllPageable(Pageable pageable) {
        return hostService.findAllPageable(pageable);
    }

    @GetMapping("/active")
    public int getActiveHosts() {
        return hostService.getActiveHosts();
    }
}
