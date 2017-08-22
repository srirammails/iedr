package pl.nask.crs.accounts.services.impl;

import ie.domainregistry.account_update_1.AccType;
import ie.domainregistry.account_update_1.AccountType;
import ie.domainregistry.account_update_1.ObjectFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import pl.nask.crs.account.AccountVersionService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.exporter.XmlExporter;
import pl.nask.crs.nichandle.NicHandle;

public class AccountUpdateExporter extends XmlExporter<AccType> {
    private final static Logger log = Logger.getLogger(AccountUpdateExporter.class);
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

    private ApplicationConfig applicationConfig;
    private AccountVersionService accountVersionService;

    public AccountUpdateExporter(ApplicationConfig appConf, AccountVersionService accVersionService) throws JAXBException, SAXException {
        super("account-update.xsd", "ie.domainregistry.account_update_1");
        applicationConfig = appConf;
        accountVersionService = accVersionService;
    }
    
    public void exportAccount(NicHandle nicHandle, String vatNo) throws ExportException {
        int nextId = accountVersionService.getNextAccountVersion();
        AccType accType = convert(nicHandle, vatNo, nextId);

        JAXBElement<AccType> jaxbElement = new ObjectFactory().createAcc(accType);

        OutputStream destination = destinationFor(nextId, nicHandle.getChangeDate());
        serialize(jaxbElement, destination, null);
    }

	public void exportAccount(long accountNumber, String accName, String vatNo, String address1, String address2,
                                 String address3, String billC, String country, String county, Date chngDate, String phone, String fax, String email, String vatCategory) throws ExportException {
        int nextId = accountVersionService.getNextAccountVersion();
        AccType accType = convert(accountNumber, accName, vatNo, address1, address2, address3, billC, country, county, chngDate, nextId, phone, fax, email, vatCategory);

        JAXBElement<AccType> jaxbElement = new ObjectFactory().createAcc(accType);

        OutputStream destination = destinationFor(nextId, chngDate);
        serialize(jaxbElement, destination, null);
    }

    private OutputStream destinationFor(int id, Date changeDate) throws AccountExportException {
        ExportConfiguration config = applicationConfig.getAccountUpdateExportConfig();
        String formattedName = NameFormatter.getFormattedName(id, NameFormatter.NamePrefix.ACC, NameFormatter.NamePostfix.xml);
        TargetFileInfo fileConfig = config.fileConfig(formattedName, changeDate);
        try {
            File f = fileConfig.getTargetFile(true);
            return new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            String msg = "Error exporting account update while creating output file" + fileConfig;
            log.error(msg);
            log.debug(msg, e);
            throw new AccountExportException(msg, e);
        }
    }
   
    private AccType convert(NicHandle nicHandle, String vatNo, int nextId) {
    	AccType accType = new AccType();
    	accType.setId(NameFormatter.getFormattedName(nextId, NameFormatter.NamePrefix.ACC));
        accType.setTimestamp(FORMATTER.format(nicHandle.getChangeDate()));
        AccountType accountType = new AccountType();
        accountType.setAccountNo(nicHandle.getAccount().getId());
        accountType.setAddress1(nicHandle.getAddress());
        accountType.setAddress2(null);
        accountType.setAddress3(null);
        accountType.setBillC(nicHandle.getNicHandleId());
        accountType.setCountry(nicHandle.getCountry());
        accountType.setCounty(nicHandle.getCounty());
        accountType.setName(nicHandle.getName());
        accountType.setVatNo(vatNo);
        accountType.setPhone(nicHandle.getPhonesAsString());
        accountType.setFax(nicHandle.getFaxesAsString());
        accountType.setEmail(nicHandle.getEmail());
        accountType.setVatCategory(nicHandle.getVatCategory());
        accType.setAccount(accountType);
		return accType;
	}

    private AccType convert(long accountNumber, String accName, String vatNo,
                            String address1, String address2, String address3,
                            String billC, String country, String county, Date chngDate,
                            int nextId, String phone, String fax, String email, String vatCategory) {
        AccType accType = new AccType();
        accType.setId(NameFormatter.getFormattedName(nextId, NameFormatter.NamePrefix.ACC));
        accType.setTimestamp(FORMATTER.format(chngDate));
        AccountType accountType = new AccountType();
        accountType.setAccountNo(accountNumber);
        accountType.setAddress1(address1);
        accountType.setAddress2(address2);
        accountType.setAddress3(address3);
        accountType.setBillC(billC);
        accountType.setCountry(country);
        accountType.setCounty(county);
        accountType.setName(accName);
        accountType.setVatNo(vatNo);
        accountType.setPhone(phone);
        accountType.setFax(fax);
        accountType.setEmail(email);
        accountType.setVatCategory(vatCategory);
        accType.setAccount(accountType);        
        return accType;
    }
}
