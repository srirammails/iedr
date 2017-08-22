package pl.nask.crs.iedrapi.impl.contact;

import ie.domainregistry.ieapi_contact_1.InfDataType;
import ie.domainregistry.ieapi_contact_1.StatusType;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import pl.nask.crs.api.vo.NicHandleVO;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;

public class ContactConversionHelper {

    public static InfDataType makeInfo(NicHandle res) {
        InfDataType t = new InfDataType();
        t.setAccount(BigInteger.valueOf(res.getAccount().getId()));
        t.setAddr(res.getAddress());
        t.setCompanyName(res.getCompanyName());
        t.setCountry(res.getCountry());
        t.setCounty(res.getCounty());
        t.setCrDate(res.getRegistrationDate());
        t.setEmail(res.getEmail());
        t.setFax(TypeConverter.setToString(res.getFaxes()));
        t.setId(res.getNicHandleId());
        t.setName(res.getName());
        t.setStatus(StatusType.fromValue(res.getStatus().name()));
        t.setVoice(TypeConverter.setToString(res.getPhones()));
        
        return t;
    }

    public static List<String> nhToId(List<NicHandleVO> nhlist) {
        List<String> res = new LinkedList<String>();
        for (NicHandleVO nh: nhlist) {
            res.add(nh.getNicHandleId());
        }
        return res ;
    }
}
