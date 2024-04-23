package org.netprophet.agent.service;

import org.netprophet.agent.exception.ParseException;
import org.netprophet.agent.interfaces.NFParser;
import org.netprophet.agent.model.NetworkFlow;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class TsharkNFParser implements NFParser {

    @Override
    public List<NetworkFlow> parseFromFile(File filename) {
        List<NetworkFlow> networkFlows = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int skipLines = 5;
            int protocol = -1;

            while ((line = br.readLine()) != null) {
                if (skipLines != 0) {
                    if (skipLines == 4) {
                        protocol = line.contains("TCP") ? 6 : 17;
                    }
                    skipLines--;
                    continue;
                }

                if (line.contains("===")) {
                    break;
                }

                String[] parts = line.split("\\s+");
                String[] srcIp = parts[0].split(":");
                String[] destIp = parts[2].split(":");

                if (srcIp.length != 2 && destIp.length != 2) {
                    continue;
                }

                if (parts.length == 14) {
                    var flowPacket = new NetworkFlow(
                            srcIp[0],
                            Integer.parseInt(srcIp[1]),
                            destIp[0],
                            Integer.parseInt(destIp[1]),
                            protocol,
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[6]),
                            Integer.parseInt(parts[7]),
                            Double.parseDouble(parts[13])
                    );
                    networkFlows.add(flowPacket);
                }
            }
        } catch (IOException e) {
            throw new ParseException(e);
        }
        return networkFlows;
    }
}
