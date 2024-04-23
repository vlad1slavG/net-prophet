package org.netprophet.agent.service;

import lombok.RequiredArgsConstructor;
import org.netprophet.agent.interfaces.ApplicationArguments;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class DefaultApplicationArguments implements ApplicationArguments {

    private final String arguments;

    @Override
    public Map<String, String> map() {
        String[] words = arguments.split(" ");
        Map<String, String> resultMap = new HashMap<>();

        String option = null;
        StringBuilder optionValue = null;
        for (String word : words) {
            if (word.startsWith("-")) {
                if (option != null) {
                    resultMap.put(option, optionValue.toString().trim());
                }
                option = word;
                optionValue = new StringBuilder();
            } else if (option != null) {
                optionValue.append(word).append(" ");
            }
        }

        if (option != null) {
            resultMap.put(option, optionValue.toString().trim());
        }

        if (resultMap.size() != 4) {
            printUsage();
            System.exit(0);
        }
        return resultMap;
    }

    private void printUsage() {
        System.out.println("Usage: java -jar agent.jar [options] ...");
        System.out.println("\t-i <interface> - name of interface");
        System.out.println("\t-h <hostname> - hostname or ipv4 address");
        System.out.println("\t-p <port> - port number");
        System.out.println("\t-d <NUM> - stop after NUM seconds");
    }
}
