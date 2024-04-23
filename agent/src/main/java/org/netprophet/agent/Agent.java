package org.netprophet.agent;

import org.netprophet.agent.config.Config;
import org.netprophet.agent.interfaces.NFSender;
import org.netprophet.agent.interfaces.NFWriter;
import org.netprophet.agent.task.NFCollectorTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Timer;

public class Agent {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        var timer = new Timer();
        var task = new NFCollectorTask(context.getBean(NFWriter.class), context.getBean(NFSender.class));
        timer.schedule(task, 1000, 1000);
    }
}
