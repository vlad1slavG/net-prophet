package org.netprophet.agent.interfaces;

import org.netprophet.agent.model.NetworkFlow;

import java.io.File;
import java.util.List;

public interface NFParser {
    List<NetworkFlow> parseFromFile(File filename);
}
