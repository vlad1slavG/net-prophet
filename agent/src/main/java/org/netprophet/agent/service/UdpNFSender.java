package org.netprophet.agent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.netprophet.agent.exception.ParseException;
import org.netprophet.agent.interfaces.ApplicationArguments;
import org.netprophet.agent.interfaces.NFParser;
import org.netprophet.agent.interfaces.NFSender;
import org.netprophet.agent.model.NetworkFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UdpNFSender implements NFSender {

    private static final Logger logger = LoggerFactory.getLogger(TsharkNFWriter.class);

    private final File workingDir;
    private final ObjectMapper objectMapper;
    private final NFParser nfParser;
    private final ApplicationArguments arguments;

    @Override
    public void send() {
        Map<String, String> argumentsMap = arguments.map();
        List<NetworkFlow> networkFlows = new ArrayList<>();

        var directory = new File(workingDir, "flows");
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    networkFlows.addAll(nfParser.parseFromFile(file));
                } catch (ParseException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        String networkFlow;
        try {
            networkFlow = objectMapper.writeValueAsString(networkFlows);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return;
        }

        if (networkFlow != null) {
            try (DatagramChannel dc = DatagramChannel.open()) {
                byte[] serializedList = networkFlow.getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(serializedList);
                SocketAddress serverAddress = new InetSocketAddress(argumentsMap.get("-h"), Integer.parseInt(argumentsMap.get("-p")));
                dc.send(buffer, serverAddress);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
