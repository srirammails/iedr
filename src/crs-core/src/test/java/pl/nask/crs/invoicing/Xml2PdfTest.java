package pl.nask.crs.invoicing;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.*;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.*;
import org.dom4j.io.SAXReader;


public class Xml2PdfTest {

    private static final Logger LOGGER = Logger.getLogger(Xml2PdfTest.class);

    static {
        LOGGER.setLevel(Level.INFO);
        LOGGER.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    }

    @Test
    public void testXml2PdfTemplate() {
    	renderPdf("xmlFile.xml");
    }
    
    private void renderPdf(String xmlFileName) {
        try {
            File outDir = new File("target", "xml2PdfOut");
            outDir.mkdirs();

            InputStream xmlFile = getClass().getResourceAsStream("/" + xmlFileName);
            InputStream xmlFileStream = replaceInvoiceTag(xmlFile);
            InputStream xsltFile = getClass().getResourceAsStream("/xml2PdfTemplate.xsl");
            File pdfFile = new File(outDir, xmlFileName + ".pdf");

            LOGGER.info("Input: XML (" + xmlFile + ")");
//            LOGGER.info("Stylesheet: " + xsltFile);
            LOGGER.info("Output: PDF (" + pdfFile + ")");
            LOGGER.info("Transforming...");

            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setStrictValidation(false);
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            String basePath = "file://" + getClass().getResource("/xml2PdfTemplate.xsl").getPath();
            foUserAgent.setBaseURL(basePath);
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

            try {
            	Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                Result res = new SAXResult(fop.getDefaultHandler());

                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

                transformer.transform(new StreamSource(xmlFileStream), res);
            } finally {
                out.close();
            }

            LOGGER.info("Success!");
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	AssertJUnit.fail("Error generating invoice, check logs for details");
        }
    }
    
    // in a normal case this should take few (about 4) seconds.
    @Test(timeOut=10*1000)
    public void testXml2PdfTemplateLargeInvoice() {
        renderPdf("largeXmlFile.xml");
    }

    private InputStream replaceInvoiceTag(InputStream xmlFile) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);
        String s = document.asXML();
        s = s.replaceAll("<invoice[^>]*>","<invoice>");
        InputStream ret = IOUtils.toInputStream(s);
        return ret;
    }
}

