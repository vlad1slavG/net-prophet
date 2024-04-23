package org.netprophet.agent.task;

import lombok.RequiredArgsConstructor;
import org.netprophet.agent.interfaces.NFSender;
import org.netprophet.agent.interfaces.NFWriter;

import java.util.TimerTask;

@RequiredArgsConstructor
public class NFCollectorTask extends TimerTask {

    private final NFWriter nfWriter;
    private final NFSender nfSender;

    @Override
    public void run() {
        nfWriter.writeToFile();
        nfSender.send();
    }
}
