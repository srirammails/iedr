package pl.nask.crs.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.app.commons.exceptions.DomainIncorrectStateException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainInNRPException;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainNameMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DuplicatedNameserverException;
import pl.nask.crs.app.tickets.exceptions.GlueNotAllowedException;
import pl.nask.crs.app.tickets.exceptions.GlueRequiredException;
import pl.nask.crs.app.tickets.exceptions.HolderCategoryMandatoryException;
import pl.nask.crs.app.tickets.exceptions.HolderClassMandatoryException;
import pl.nask.crs.app.tickets.exceptions.HolderRemarkTooLongException;
import pl.nask.crs.app.tickets.exceptions.IpSyntaxException;
import pl.nask.crs.app.tickets.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.app.tickets.exceptions.TooFewNameserversException;
import pl.nask.crs.app.tickets.exceptions.TooManyNameserversException;
import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.NameserverChange;
import static pl.nask.crs.commons.utils.Validator.isEmpty;

public final class ValidationHelper {
	
	private ValidationHelper() {}

	public static void validateMandatoryFields(Ticket ticket) throws DomainNameMandatoryException, HolderRemarkTooLongException {
        if (isEmpty(ticket.getOperation().getDomainNameField().getNewValue()))
            throw new DomainNameMandatoryException();

        if (!isEmpty(ticket.getRequestersRemark()) && ticket.getRequestersRemark().length() > 500)
            throw new HolderRemarkTooLongException();
    }

    public static void validateHolder(Ticket ticket) throws DomainHolderMandatoryException, HolderCategoryMandatoryException, HolderClassMandatoryException {
        if (isEmpty(ticket.getOperation().getDomainHolderField().getNewValue()))
            throw new DomainHolderMandatoryException();

        if (isEmpty(ticket.getOperation().getDomainHolderClassField().getNewValue()))
            throw new HolderClassMandatoryException();

        if (isEmpty(ticket.getOperation().getDomainHolderCategoryField().getNewValue()))
            throw new HolderCategoryMandatoryException();
    }

    public static void checkDomainNsc(String domainName, List<NameserverChange> nsList, int min, int max) throws TooFewNameserversException, TooManyNameserversException, DuplicatedNameserverException, NameserverNameSyntaxException, IpSyntaxException, GlueNotAllowedException, GlueRequiredException {
        List<Nameserver> nameservers = new ArrayList<Nameserver>(nsList.size());
        for (NameserverChange chng : nsList) {
            final String name = chng.getName().getNewValue();
            final String ipv4 = chng.getIpAddress().getNewValue();
            if (name != null) {
                nameservers.add(new Nameserver(name, Validator.isEmpty(ipv4) ? null : new IPAddress(ipv4)));
            }
        }
        checkDomainNs(domainName, nameservers, min, max);
    }

    public static void checkDomainNs(String domainName, List<Nameserver> nsList, int min, int max) throws TooFewNameserversException, TooManyNameserversException, DuplicatedNameserverException, NameserverNameSyntaxException, IpSyntaxException, GlueNotAllowedException, GlueRequiredException {
        checkDomainListSize(min, max, nsList.size());
        checkForNSDuplications(nsList);

        domainName = domainName.toLowerCase();
        for (Nameserver ns : nsList) {
            String nsName = ns.getName() == null ? null : ns.getName().toLowerCase();
            try {
                DomainNameValidator.validateName(nsName);
            } catch (InvalidDomainNameException e) {
                throw new NameserverNameSyntaxException(e, nsName);
            }

            if (ns.getIpAddress() != null && !isEmpty(ns.getIpAddress().getAddress()) && !ns.getIpAddress().getAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}"))
                throw new IpSyntaxException(ns.getIpAddress().getAddress());

            if (ns.getIpAddress() != null && !isEmpty(ns.getIpAddress().getAddress()) && !nsName.endsWith(domainName))
                throw new GlueNotAllowedException(nsName);

            if ((ns.getIpAddress() == null || isEmpty(ns.getIpAddress().getAddress())) && nsName.endsWith(domainName))
                throw new GlueRequiredException(nsName);
        }
    }

	public static void validateAccountId(long id, Domain domain) throws DomainManagedByAnotherResellerException {
        if (id != domain.getResellerAccount().getId())
            throw new DomainManagedByAnotherResellerException(domain.getName());
    }

    public static void validateNRP(Domain domain) throws DomainInNRPException {
    	if (domain.isNRP())
            throw new DomainInNRPException(domain.getName());
    }

    public static void checkNotInTransferPendingActiveOrPostTransactionAudit(Domain domain) 
            throws DomainIncorrectStateException {
        if (domain.getDsmState().getNRPStatus().isTransferPendingActiveOrPostTransactionAudit()) {
            throw new DomainIncorrectStateException(
                "Domain is in TransferPendingActive or PostTransactionAudit state.", domain.getName()
            );
        }
    }

    public static void checkIsNotWIPO(Domain domain) 
            throws DomainIncorrectStateException {
        if (domain.getDsmState().getWipoDispute()) {
            throw new DomainIncorrectStateException("Domain is WIPO.", domain.getName());
        }
    }

    private static void checkForNSDuplications(List<Nameserver> nsList) throws DuplicatedNameserverException {
        Set<String> nsSet = new HashSet<String>();
        for (Nameserver ns : nsList) {
            if (!nsSet.add(ns.getName()))
                throw new DuplicatedNameserverException(ns.getName());
        }
    }

    private static void checkDomainListSize(int min, int max, int size) throws TooFewNameserversException, TooManyNameserversException {
        if (size < min)
            throw new TooFewNameserversException();

        if (max >= 0 && size > max)
            throw new TooManyNameserversException();
    }
}
