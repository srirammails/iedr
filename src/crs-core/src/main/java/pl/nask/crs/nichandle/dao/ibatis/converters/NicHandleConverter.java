package pl.nask.crs.nichandle.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.Vat;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.nichandle.dao.ibatis.objects.Telecom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleConverter extends AbstractConverter<InternalNicHandle, NicHandle> {

    protected NicHandle _to(InternalNicHandle internalNicHandle) {

        Set<String> phones = new HashSet<String>();
        Set<String> faxes = new HashSet<String>();
        for (Telecom telecom : internalNicHandle.getTelecoms()) {
            if (telecom.getType() == Telecom.Type.FAX) {
                faxes.add(telecom.getNumber());
            } else {
                phones.add(telecom.getNumber());
            }
        }
        Account account = new Account(internalNicHandle.getAccountNumber(), internalNicHandle.getAccountName());
        account.setAgreementSigned(internalNicHandle.isAgreementSigned());
        account.setTicketEdit(internalNicHandle.isTicketEdit());
        Vat vat = new Vat(internalNicHandle.getVatNo());
        return new NicHandle(
                internalNicHandle.getNicHandleId(),
                internalNicHandle.getName(),
                account,
                internalNicHandle.getCompanyName(),
                internalNicHandle.getAddress(),
                phones.isEmpty() ? null : phones,
                faxes.isEmpty() ? null : faxes,
                internalNicHandle.getCounty(),
                internalNicHandle.getCountry(),
                internalNicHandle.getEmail(),
                internalNicHandle.getStatus(),
                internalNicHandle.getStatusChangeDate(),
                internalNicHandle.getRegistrationDate(),
                internalNicHandle.getChangeDate(),
                internalNicHandle.isBillCInd(),
                internalNicHandle.getNicHandleRemark(),
                internalNicHandle.getCreator(),
                vat,
                internalNicHandle.getVatCategory()
        );
    }

    protected InternalNicHandle _from(NicHandle nicHandle) {
        List<Telecom> telecoms = new ArrayList<Telecom>();
        if (nicHandle.getPhones() != null)
            for (String phoneNumber : nicHandle.getPhones())
                telecoms.add(new Telecom(phoneNumber, Telecom.Type.PHONE));
        if (nicHandle.getFaxes() != null)
            for (String faxNumber : nicHandle.getFaxes())
                telecoms.add(new Telecom(faxNumber, Telecom.Type.FAX));

        return  new InternalNicHandle(
                nicHandle.getNicHandleId(),
                nicHandle.getName(),
                nicHandle.getAccount().getId(),
                nicHandle.getAccount().getName(),
                nicHandle.getCompanyName(),
                nicHandle.getAddress(),
                telecoms,
                nicHandle.getCounty(),
                nicHandle.getCountry(),
                nicHandle.getEmail(),
                nicHandle.getStatus(),
                nicHandle.getStatusChangeDate(),
                nicHandle.getRegistrationDate(),
                nicHandle.getChangeDate(),
                nicHandle.isBillCInd(),
                nicHandle.getNicHandleRemark(),
                nicHandle.getCreator(),
                nicHandle.getVat().getVatNo(),
                nicHandle.getVatCategory()
        );
    }


}
