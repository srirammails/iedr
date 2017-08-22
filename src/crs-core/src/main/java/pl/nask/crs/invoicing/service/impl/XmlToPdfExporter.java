package pl.nask.crs.invoicing.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ExportConfiguration;
import pl.nask.crs.commons.config.InvoiceExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.payment.Invoice;

public class XmlToPdfExporter implements InvoiceExporter {
	private static final Logger LOGGER = Logger.getLogger(XmlToPdfExporter.class);
	private final ApplicationConfig applicationConfig;
    private final static String IEDR_LOGO_FILE_NAME = "iedrLogo.tif";

	public XmlToPdfExporter(ApplicationConfig applicationConfig) { 
		this.applicationConfig = applicationConfig;
	}
	
	@Override
	public void export(Invoice invoice) throws ExportException {	
		doExport(invoice.getInvoiceNumber(), invoice.getInvoiceDate());
	}

	void doExport(String invoiceNumber, Date date) {
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			File originalXmlFile = getSourceFile(invoiceNumber, date);
            InputStream xmlFileStream = replaceInvoiceTag(originalXmlFile);
			File pdfFile = getTargetFile(invoiceNumber, date);

			File outDir = new File("target", "xml2PdfOut");
			outDir.mkdirs();

			InputStream xsltFile = getClass().getResourceAsStream("/xml2PdfTemplate.xsl");

			LOGGER.info("Input: XML (" + originalXmlFile + ")");
			LOGGER.info("Output: PDF (" + pdfFile + ")");
			LOGGER.info("Transforming...");

			FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setStrictValidation(false);
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            foUserAgent.setURIResolver(new MyURIResolver());

            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

			try {
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
				Result res = new SAXResult(fop.getDefaultHandler());

				TransformerFactory factory = TransformerFactory.newInstance();
                factory.setURIResolver(new MyURIResolver());
				Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

				transformer.transform(new StreamSource(xmlFileStream), res);
			} finally {
				out.close();
			}
			stopWatch.stop();
			LOGGER.debug("Success! Export took " + stopWatch.toString());
			if (stopWatch.getTime() > 2 * 60 * 1000) {
				LOGGER.warn("Export took more than 2 minutes!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

    private InputStream replaceInvoiceTag(File xmlFile) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);
        String s = document.asXML();
        s = s.replaceAll("<invoice[^>]*>","<invoice>");
        InputStream ret = IOUtils.toInputStream(s);
        return ret;
    }

    private File getTargetFile(String invoiceNumber, Date date) {
		ExportConfiguration cfg = applicationConfig.getPdfInvoiceExportConfig();
        String formattedName = NameFormatter.getFormattedName(invoiceNumber, NameFormatter.NamePostfix.pdf);
		TargetFileInfo fileInfo = cfg.fileConfig(formattedName, date);
		File file = fileInfo.getTargetFile(true);		

		return file;
	}

	private File getSourceFile(String invoiceNumber, Date date) throws PdfExportException {
		InvoiceExportConfiguration sourceCfg = (InvoiceExportConfiguration) applicationConfig.getXmlInvoiceExportConfig();
        String formattedName = NameFormatter.getFormattedName(invoiceNumber, NameFormatter.NamePostfix.xml);
		TargetFileInfo srcFile = sourceCfg.archiveFileConfig(formattedName, date);
		File xmlFile = srcFile.getTargetFile(false);
		if (!xmlFile.exists()) {
			throw new PdfExportException("Source file does not exist: " + srcFile);
		}

		return xmlFile;
	}

    private class MyURIResolver implements URIResolver {
        @Override
        public Source resolve(String href, String base) throws TransformerException {
            if(IEDR_LOGO_FILE_NAME.equals(href))
                return new StreamSource(getClass().getResourceAsStream("/" + IEDR_LOGO_FILE_NAME));
            return null;
        }
    }
}
