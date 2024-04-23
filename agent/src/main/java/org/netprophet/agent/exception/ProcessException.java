package org.netprophet.agent.exception;

public class ProcessException extends RuntimeException {

    public ProcessException(String msg) {
        super(msg);
    }

    public ProcessException(Exception e) {
        super(e);
    }
}
