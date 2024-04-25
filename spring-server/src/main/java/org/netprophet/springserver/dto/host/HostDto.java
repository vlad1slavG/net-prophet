package org.netprophet.springserver.dto.host;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class HostDto {
    private String hostName;
    private String hostIp;
    private Instant lastActiveDateTime;
}
