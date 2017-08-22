package pl.nask.crs.dnscheck;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotification {

    private int id;
    private String nicHandleId;
    private String email;
    private String domainName;
    private String nsName;
    private String errorMessage;

    private String timeOfCheck;
    private String checkOutput;

    public DnsNotification() {
    }

    public DnsNotification(String nicHandleId, String email, String domainName, String nsName, String timeOfCheck, String checkOutput) {
        this.nicHandleId = nicHandleId;
        this.email = email;
        this.domainName = domainName;
        this.nsName = nsName;

        this.timeOfCheck = timeOfCheck;
        this.checkOutput = checkOutput;
        composeErrorMessage();
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getNsName() {
        return nsName;
    }

    public void setNsName(String nsName) {
        this.nsName = nsName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        parseErrorMessage();
    }

    /**
     * Parses error message looking for time of check. Time of error should be present at the begining of message in format
     * [hh:mm]\n
     * @return String containing hour and minute of check or null if no time was found
     */
    public String getTimeOfCheck() {
        return timeOfCheck;
    }

    public String getCheckOutput() {
        return checkOutput;
    }

    /**
     * Parses error message into actual check output and time of check.
     * Error message is supposed to have format:
     * <pre>
     * [hh:mm]\n
     * output
     * </pre>
     */
    private void parseErrorMessage() {
        int firstLineEnd = errorMessage.indexOf('\n');
        String firstLine = firstLineEnd >= 0 ? errorMessage.substring(0, firstLineEnd) : errorMessage;

        if (firstLine.matches("\\[[0-9]{2}:[0-9]{2}\\]")) {
            timeOfCheck = firstLine.substring(1, firstLine.length() - 1);
            checkOutput = errorMessage.substring(firstLineEnd + 1);
        } else {
            timeOfCheck = null;
            checkOutput = errorMessage;
        }
    }

    private void composeErrorMessage() {
        StringBuilder builder = new StringBuilder(checkOutput.length() + timeOfCheck.length() + 3);
        builder.append('[').append(timeOfCheck).append("]\n").append(checkOutput);
        errorMessage = builder.toString();
    }

    @Override
    public String toString() {
        return String.format("DnsNotification[id: %s, nicHandle: %s, domainName: %s, nsName: %s]", id, nicHandleId, domainName, nsName);
    }
}
