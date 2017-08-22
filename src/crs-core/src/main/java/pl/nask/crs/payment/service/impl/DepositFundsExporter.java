package pl.nask.crs.payment.service.impl;

import ie.domainregistry.doa_1.AccountType;
import ie.domainregistry.doa_1.DoaType;
import ie.domainregistry.doa_1.ObjectFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.exporter.XmlExporter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;

public class DepositFundsExporter extends XmlExporter<DoaType> {

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
    private final NicHandleSearchService nhService;
	private final SequentialNumberGenerator generator;
	private final ApplicationConfig cfg;
	private AccountSearchService accountSearchService;
	
	public DepositFundsExporter(ApplicationConfig cfg, NicHandleSearchService nhService, AccountSearchService accountSearchService, SequentialNumberGenerator generator) throws JAXBException, SAXException {
		super("doa.xsd", "ie.domainregistry.doa_1");
		this.cfg = cfg;
		this.nhService = nhService;
		this.accountSearchService = accountSearchService;
		this.generator = generator;
	}
	
	public void exportDOA(String nicHandle, double valueInStandardCurrencyUnit, Date date, String orderId) throws ExportException, NicHandleNotFoundException {
		NicHandle nh = nhService.getNicHandle(nicHandle);
		Account acc = accountSearchService.getAccount(nh.getAccount().getId());
		nh.setAccount(acc);
		long nextId = generator.getNextId();
		DoaType dt = convert(nh, valueInStandardCurrencyUnit, nextId, date, orderId);
		ObjectFactory of = new ObjectFactory();
		OutputStream destination = destinationFor(nextId, date);
		serialize(of.createDoa(dt), destination, null);
	}

	private OutputStream destinationFor(long nextId, Date date) throws ExportException {
		ExportConfiguration config = cfg.getDoaExportConfiguration();
        String formattedName = NameFormatter.getFormattedName(nextId, NameFormatter.NamePrefix.DOA, NameFormatter.NamePostfix.xml);
        TargetFileInfo fileConfig = config.fileConfig(formattedName, date);
		
		try {
			File f = fileConfig.getTargetFile(true);

			return new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			throw new ExportException("Couldn't create output file " + fileConfig, e);
		}
	}
	
	private DoaType convert(NicHandle nh, double valueInStandardCurrencyUnit, long nextId, Date date, String orderId) {
		DoaType dt = new DoaType();
		AccountType acc = new AccountType();
		acc.setNumber(nh.getAccount().getId());
		acc.setBillC(nh.getAccount().getBillingContact().getNicHandle());
		dt.setAccount(acc );
		dt.setAdjustment(false);
		dt.setAmount(valueInStandardCurrencyUnit);
		dt.setId(NameFormatter.getFormattedName(nextId, NameFormatter.NamePrefix.DOA));
		dt.setRemark("top up");
		dt.setTimestamp(FORMATTER.format(date));
		dt.setOrderId(orderId);
		return dt;
	}

}
