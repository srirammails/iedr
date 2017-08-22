package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_domain_1.ContactType;
import ie.domainregistry.ieapi_domain_1.DsmHolderType;
import ie.domainregistry.ieapi_domain_1.DsmNrp;
import ie.domainregistry.ieapi_domain_1.DsmRenewalMode;
import ie.domainregistry.ieapi_domain_1.DsmStateType;
import ie.domainregistry.ieapi_domain_1.DsmWipo;
import ie.domainregistry.ieapi_domain_1.HolderNameType;
import ie.domainregistry.ieapi_domain_1.HolderType;
import ie.domainregistry.ieapi_domain_1.InfDataType;
import ie.domainregistry.ieapi_domain_1.NsType;
import ie.domainregistry.ieapi_domain_1.PublishedType;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.api.vo.AbstractTicketRequestVO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * @author: Marcin Tkaczyk
 */
public final class DomainConversionHelper {
	
	private DomainConversionHelper() {
		
	}

    public static InfDataType makeInfo(Domain res) {
        return makeInfo(res, false);
    }

    public static InfDataType makeInfo(Domain res, boolean inclAuthCode) {
        InfDataType result = new InfDataType();
        result.setAccount(BigInteger.valueOf(res.getResellerAccount().getId()));
        result.setStatus(convertDsmState(res.getDsmState(), res.isPublished()));

        HolderType holder = new HolderType();
        HolderNameType holderName = new HolderNameType();
        holderName.setCategory(res.getHolderCategory());
        holderName.setValue(res.getHolder());
        holder.setHolderName(holderName);
        holder.setClazz(res.getHolderClass());
        holder.setHolderRemarks(res.getRemark());
        result.setHolder(holder);

        List<Nameserver> nsVO = res.getNameservers();
        for (Nameserver n : nsVO) {
            NsType ns = new NsType();
            ns.setNsName(n.getName());
            if (!Validator.isEmpty(n.getIpAddressAsString())) {
                ns.setNsAddr(n.getIpAddressAsString());
            }
            result.getNs().add(ns);
        }

        for (Contact c : res.getAdminContacts()) {
            ContactType cType = new ContactType();
            cType.setType(ContactAttrType.ADMIN);
            cType.setValue(c.getNicHandle());
            result.getContact().add(cType);
        }
        //TODO: what about billing contact?
//        for (ContactVO c : res.getDomain().getBillingContacts()) {
//        ContactType cType = new ContactType();

//            cType.setType(ContactAttrType.);
//            cType.setValue(c.getName());
//            result.getContact().add(cType);
//        }
        for (Contact c : res.getTechContacts()) {
            ContactType cType = new ContactType();
            cType.setType(ContactAttrType.TECH);
            cType.setValue(c.getNicHandle());
            result.getContact().add(cType);
        }

        result.setName(res.getName());
        result.setRegDate(res.getRegistrationDate());
        result.setRenDate(res.getRenewDate());
        result.setSuspendDate(res.getSuspensionDate());
        result.setDeleteDate(res.getDeletionDate());

        if (inclAuthCode) {
            result.setAuthCode(res.getAuthCode());
        }

        return result;
    }

    public static DsmStateType convertDsmState(DsmState dsmState, boolean isPublished) {
        if (dsmState == null) {
            dsmState = DsmState.initialState();
        }
		DsmStateType state = new DsmStateType();
		state.setHolderType(convertHolderType(dsmState));
		state.setRenewalStatus(convertNrp(dsmState));
		state.setPublished(convertPublished(isPublished));
		state.setRenewalMode(convertRenMode(dsmState));
		state.setWipo(convertWipo(dsmState));
		
		return state;
	}

    private static PublishedType convertPublished(boolean isPublished) {
		return isPublished ? PublishedType.Y : PublishedType.N;
	}

	private static DsmWipo convertWipo(DsmState dsmState) {
		if (dsmState.getWipoDispute() != null && dsmState.getWipoDispute()) {
			return DsmWipo.Y;
		} else {
			return DsmWipo.N;
		}
	}

	private static DsmRenewalMode convertRenMode(DsmState dsmState) {
        switch (dsmState.getRenewalMode()) {
            case Autorenew:
                return DsmRenewalMode.AUTORENEW;
            case NoAutorenew:
                return DsmRenewalMode.NO_AUTORENEW;
            case RenewOnce:
                return DsmRenewalMode.RENEW_ONCE;
            case NA:
                return DsmRenewalMode.N_A;
            default:
                throw new IllegalArgumentException("Illegal renewal mode: " + dsmState.getRenewalMode());
        }
	}

	private static DsmNrp convertNrp(DsmState dsmState) {		
		switch(dsmState.getNRPStatus()) {
		case Active:
		case TransferPendingActive:
		case PostTransactionAudit:
		case TransactionFailed:
		case InvoluntaryMailedPaymentPending:
		case InvoluntarySuspendedPaymentPending:
		case TransferPendingInvNrp:
		case TransferPendingVolNRP:
			return DsmNrp.ACTIVE;
		case InvoluntaryMailed:
			return DsmNrp.INVOLUNTARY_NRP;
		case VoluntaryMailed:
			return DsmNrp.VOLUNTARY_NRP;
		case InvoluntarySuspended:
			return DsmNrp.INVOLUNTARY_NRP_SUSPENDED;
		case VoluntarySuspended:
			return DsmNrp.VOLUNTARY_NRP_SUSPENDED;
        case NA:
            return DsmNrp.N_A;
		case Deleted:
		default:
			throw new IllegalArgumentException("Cannot map " + dsmState + " onto IEDR-API DsmState type");
		}
	}

	private static DsmHolderType convertHolderType(DsmState dsmState) {
		return DsmHolderType.fromValue(dsmState.getDomainHolderType().name());
	}

	public static List<Nameserver> toNameserverList(List<NsType> nsTypeList) {
        if (nsTypeList == null)
            return null;
        List<Nameserver> res = new ArrayList<Nameserver>();
        int i = 0;
        for (NsType ns : nsTypeList) {
            Nameserver nsVO = new Nameserver(ns.getNsName(), new IPAddress(ns.getNsAddr() == null ? "" : ns.getNsAddr()), i);
            res.add(nsVO);
            i++;
        }
        return res;
    }

	public static List<Contact> toContacts(List<String> contacts) {
		if (contacts == null)
			return null;
		List<Contact> l= new ArrayList<Contact>();
		for (String c: contacts) {
			l.add(new Contact(c));
		}
		return l;
	}


    public static void updateNs(AbstractTicketRequestVO request, List<NsType> nsList) {
        request.resetToNameservers(toNameserverList(nsList));
    }
}
