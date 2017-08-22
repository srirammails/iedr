package pl.nask.crs.invoicing.service.impl;

import ie.domainregistry.invoice_1.InvoiceType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import pl.nask.crs.accounts.services.AccountHelperService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.exporter.XmlExporter;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.invoicing.service.InvoiceConversionException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.price.DomainPricingDictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.vat.VatDictionary;
import pl.nask.crs.vat.dao.VatDAO;

public class InvoiceXMLExporter extends XmlExporter<InvoiceType> implements InvoiceExporter {
	private final static Logger log = Logger.getLogger(InvoiceXMLExporter.class);
	
	private final VatDAO vatDAO;
	private final DomainPricingDAO domainPricingDAO;

	private final DomainSearchService domainSearchService;
	private final AccountHelperService accountHelperService;

	private final ApplicationConfig applicationConfig;

	public InvoiceXMLExporter(VatDAO vatDAO,
			ApplicationConfig applicationConfig,
			DomainPricingDAO domainPricingDAO, DomainSearchService domainSearchService, AccountHelperService accountHelperService) throws JAXBException, SAXException {
		super("invoiceSchema.xsd", "ie.domainregistry.invoice_1");
		this.vatDAO = vatDAO;
		this.applicationConfig = applicationConfig;
		this.domainPricingDAO = domainPricingDAO;
		this.domainSearchService = domainSearchService;
		this.accountHelperService = accountHelperService;
	}
	
	@Override
	public void export(Invoice invoice) throws ExportException {		
		VatDictionary vatDictionary = new VatDictionary(vatDAO, invoice.getInvoiceDate());
		
		InvoiceToXmlConverter converter = new InvoiceToXmlConverter(vatDictionary, new DomainPricingDictionary(domainPricingDAO), domainSearchService, accountHelperService, applicationConfig);
		JAXBElement<InvoiceType> jaxbInvoice;
		try {
			jaxbInvoice = converter.convertToJAXBInvoice(invoice);
		} catch (InvoiceConversionException e) {
			throw new InvoiceExportException("Error preparing JAXB invoice, invoiceId: " + invoice.getId(), e);
		}

        ExportConfiguration xmlInvoiceExportConfiguration = applicationConfig.getXmlInvoiceExportConfig();
		OutputStream destination = destinationFor(invoice.getInvoiceNumber(), invoice.getInvoiceDate(), xmlInvoiceExportConfiguration);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new InvoiceExportException("Cannot use MD5 as a digest alhorithm", e);
		}
		DigestOutputStream dos = new DigestOutputStream(destination, md);

		invoice.updateMD5(new String(Hex.encodeHex(md.digest())));
        invoice.setCompleted(true);

        ExportConfiguration abmXmlInvoiceExportConfiguration = applicationConfig.getABMXmlInvoiceExportConfig();
        OutputStream abmDestination = destinationFor(invoice.getInvoiceNumber(), invoice.getInvoiceDate(), abmXmlInvoiceExportConfiguration);
        DigestOutputStream abmDos = new DigestOutputStream(abmDestination, md);

        serialize(jaxbInvoice, dos, abmDos);
    }

	private OutputStream destinationFor(String invoiceNumber, Date invoiceDate, ExportConfiguration exportConfiguration) throws InvoiceExportException {
		try {
            String formattedName = NameFormatter.getFormattedName(invoiceNumber, NameFormatter.NamePostfix.xml);
			TargetFileInfo config = exportConfiguration.fileConfig(formattedName, invoiceDate);
			return new FileOutputStream(config.getTargetFile(true));
		} catch (FileNotFoundException e) {
			String msg = String.format("Error exporting invoice %s : error creating output file", invoiceNumber);
			log.error(msg);
			log.debug(msg, e);
			throw new InvoiceExportException(msg, e);
		}
	}
}
