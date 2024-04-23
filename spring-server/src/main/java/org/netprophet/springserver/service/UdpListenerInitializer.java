package org.netprophet.springserver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.netprophet.springserver.model.NetworkFlow;
import org.netprophet.springserver.repository.NetworkFlowRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UdpListenerInitializer implements ApplicationRunner {

    private final NetworkFlowRepository networkFlowRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (DatagramChannel serverSocket = DatagramChannel.open()) {
            serverSocket.configureBlocking(false);
            try (Selector selector = Selector.open()) {
                serverSocket.bind(new InetSocketAddress("localhost", 47365));
                serverSocket.register(selector, SelectionKey.OP_READ);

                ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }

                    var selectedKeys = selector.selectedKeys();
                    var itr = selectedKeys.iterator();
                    while (itr.hasNext()) {
                        var key = itr.next();
                        if (key.isReadable()) {
                            buffer.clear();
                            serverSocket.receive(buffer);
                            buffer.flip();
                            byte[] receivedData = new byte[buffer.remaining()];
                            buffer.get(receivedData);

                            TypeReference<List<NetworkFlow>> typeReference = new TypeReference<>(){};

                            List<NetworkFlow> list = objectMapper.readValue(receivedData, typeReference);
                            networkFlowRepository.saveAll(list);
                        }
                        itr.remove();
                    }
                }
            }
        }
    }
}
