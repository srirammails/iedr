package pl.nask.crs.dnscheck;

import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DnsCheckService {
    /**
     * Performs dns check for given parameters
     * @param domainName nameserver domain name
     * @param nsName nameserver name
     * @param nsAddress nameserver ip address
     * @throws DnsCheckProcessingException when internal exeption occurs during script execution
     * @throws HostNotConfiguredException when given nameserver is not configured for domain
     */
    void check(String username, String domainName, String nsName, String nsAddress) throws DnsCheckProcessingException, HostNotConfiguredException;

}
