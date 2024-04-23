package org.netprophet.agent.model;

public record NetworkFlow(
        String srcIp,
        int srcPort,
        String destIp,
        int destPort,
        int protocol,
        int packetsIn,
        int bytesIn,
        int packetsOut,
        int bytesOut,
        double duration
) {}
