package pl.nask.crs.dnscheck.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class HostNotConfiguredException extends Exception {

    private final String nsName;
    private final String fatalMessage;
    private final String fullOutputMessage;

    public HostNotConfiguredException(String nsName, String fatalMessage, String fullMessage) {
        super("Host :" + nsName + " not configured. Reason :" + fatalMessage);
        this.nsName = nsName;
        this.fatalMessage = fatalMessage;
        this.fullOutputMessage = fullMessage;
    }

    public String getNsName() {
        return nsName;
    }

    public String getFatalMessage() {
        return fatalMessage;
    }

    public String getFullOutputMessage() {
        return getFullOutputMessage(false);
    }

    public String getFullOutputMessage(boolean onlyFatalLines) {
        if (!onlyFatalLines) {
            return fullOutputMessage;
        }
        StringBuilder result = new StringBuilder();
        String[] lines = fullOutputMessage.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("FATAL"))
                result.append(line).append("\n");
        }
        return result.toString();
    }
}
