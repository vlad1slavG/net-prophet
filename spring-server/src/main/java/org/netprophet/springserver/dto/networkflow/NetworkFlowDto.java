package org.netprophet.springserver.dto.networkflow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NetworkFlowDto {
    private String srcIp;
    private Integer srcPort;
    private String destIp;
    private Integer destPort;
    private Integer protocol;
    private Integer packetsIn;
    private Integer bytesIn;
    private Integer packetsOut;
    private Integer bytesOut;
    private Double duration;
    private Instant collectedDateTime;
}
