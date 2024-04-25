package org.netprophet.springserver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.netprophet.springserver.model.Host;
import org.netprophet.springserver.model.NetworkFlow;
import org.netprophet.springserver.repository.HostRepository;
import org.netprophet.springserver.repository.NetworkFlowRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UdpListenerInitializer implements ApplicationRunner {

    private final NetworkFlowRepository networkFlowRepository;
    private final HostRepository hostRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (DatagramChannel serverSocket = DatagramChannel.open()) {
            serverSocket.configureBlocking(false);
            try (Selector selector = Selector.open()) {
                serverSocket.bind(new InetSocketAddress("192.168.240.235", 47365));
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
                            InetSocketAddress sock = (InetSocketAddress) serverSocket.receive(buffer);
                            byte[] receivedData = buffer.array();

                            updateHost(sock);
                            saveFlowToElastic(receivedData);
                        }
                        itr.remove();
                    }
                }
            }
        }
    }

    private void updateHost(InetSocketAddress sock) {
        Optional<Host> host = hostRepository.findHostByHostIp(sock.getAddress().getHostAddress());
        if (host.isPresent()) {
            Host temp = host.get();
            temp.setLastActiveDateTime(Instant.now());
            hostRepository.save(host.get());
        } else {
            Host temp = new Host(sock.getHostName(), sock.getAddress().getHostAddress());
            hostRepository.save(temp);
        }
    }

    private void saveFlowToElastic(byte[] data) throws IOException {
        TypeReference<List<NetworkFlow>> typeReference = new TypeReference<>(){};
        List<NetworkFlow> list = objectMapper.readValue(data, typeReference);
        networkFlowRepository.saveAll(list);
    }
}
