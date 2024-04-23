package org.netprophet.agent.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.netprophet.agent.exception.ProcessException;
import org.netprophet.agent.interfaces.ApplicationArguments;
import org.netprophet.agent.interfaces.NFWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TsharkNFWriter implements NFWriter {

    private static final Logger logger = LoggerFactory.getLogger(TsharkNFWriter.class);

    private final File workingDir;
    private final ApplicationArguments arguments;
    private final ProcessBuilder processBuilder = new ProcessBuilder();

    @PostConstruct
    public void init() {
        processBuilder.directory(workingDir);
    }

    @Override
    public void writeToFile() {
        try {
            createFolders();
            captureNetworkPackets();
            convertPacketsToFlows();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Writing flows to file complete");
    }

    private void captureNetworkPackets() {
        Map<String, String> argumentMap = arguments.map();
        logger.info("Initiated network packet capture on interface " + argumentMap.get("-i"));

        String pcapPath = "captures" + File.separator + "dump.pcap";
        processBuilder.command("tshark", "-a", "duration:" + argumentMap.get("-d"), "-i", argumentMap.get("-i"), "-w", pcapPath);
        runProcess();
    }

    private void convertPacketsToFlows() {
        var protocols = new String[] {"tcp", "udp"};
        String pcapPath = "captures" + File.separator + "dump.pcap";
        logger.info("Writing flow data to " + workingDir + File.separator + "flows" + File.separator);

        File flowPath;
        for (var i : protocols) {
            flowPath = Paths.get(workingDir + File.separator + "flows" + File.separator + i + "_flow.conv").toFile();
            processBuilder.command("tshark", "-r", pcapPath, "-qz", "conv," + i)
                    .redirectOutput(flowPath);
            runProcess();
        }
    }

    private void runProcess() {
        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new ProcessException("Task terminated with exit code: " + exitCode);
            }
        } catch (Exception e) {
            throw new ProcessException(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void createFolders() throws IOException {
        var folders = new String[] {
                workingDir + File.separator + "captures",
                workingDir + File.separator + "flows"
        };

        Path dir;
        for (var i : folders) {
            dir = Paths.get(i);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
        }
    }
}
