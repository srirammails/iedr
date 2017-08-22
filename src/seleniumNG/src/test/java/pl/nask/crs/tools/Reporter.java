package pl.nask.crs.tools;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Reporter {
    private int testCount = 1;
    
    private File reportsDir;

    private Configuration cfg;
    private SimpleDateFormat formatter;

    private File currentTestDir;
    private int imageCount = 1;
    private Map testModel;
    private Map reportModel = new HashMap();
    
    public static final String SUMMARY_TEMPLATE = "summary.ftl";
    public static final String TEST_TEMPLATE = "test.ftl";
    public static final String SHADOW_IMG = "shadow.gif";
    public static final String SHADOW_ALPHA_IMG = "shadowAlpha.png";

    public Reporter(File reportsDir) throws AWTException, IOException {
        this.reportsDir = reportsDir;
        this.reportsDir.mkdirs();

        File templatesDir = new File(reportsDir, "templates");
        templatesDir.mkdirs();
        FileUtils.copyURLToFile(this.getClass().getClassLoader().getResource(SUMMARY_TEMPLATE), 
        		new File(templatesDir, SUMMARY_TEMPLATE));
        FileUtils.copyURLToFile(this.getClass().getClassLoader().getResource(TEST_TEMPLATE), 
        		new File(templatesDir, TEST_TEMPLATE));
        
        File cssImagesDir = new File(reportsDir, "css");
        cssImagesDir.mkdirs();
        FileUtils.copyURLToFile(this.getClass().getClassLoader().getResource(SHADOW_ALPHA_IMG), 
                new File(cssImagesDir, SHADOW_ALPHA_IMG));
        FileUtils.copyURLToFile(this.getClass().getClassLoader().getResource(SHADOW_IMG), 
                new File(cssImagesDir, SHADOW_IMG));
        
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(templatesDir);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        
        formatter = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss z");
    }
    
    void startTest(String testName) {
        imageCount = 1;
        currentTestDir = new File(reportsDir, testName + "-test" + (testCount++));
        currentTestDir.mkdirs();
        
        testModel = new HashMap();
        testModel.put("name", testName + "-test" + (testCount - 1));
        testModel.put("screenshots", new SimpleSequence());        
        testModel.put("start", formatter.format(new Date()));
        //mapa wymagana do podsumowania
        if (reportModel.get("tests") == null) {
            reportModel.put("tests", new SimpleSequence());            
            ((SimpleSequence) reportModel.get("tests")).add(testName + "-test" + (testCount - 1));
        } else {
            ((SimpleSequence) reportModel.get("tests")).add(testName + "-test" + (testCount - 1));                        
        }
    }

    void endTest() {
        File summary = new File(currentTestDir, "index.html");
        Writer out = null;
        try {
            Template temp = cfg.getTemplate(TEST_TEMPLATE);        
            out = new FileWriter(summary);
            temp.process(testModel, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally { 
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void recordStep(String screenStream, String description) {
        File screenshotFile = new File(currentTestDir, "image" + (imageCount++) + ".png");
        try {
            InputStream in = MimeUtility.decode(new ByteArrayInputStream(screenStream.getBytes()),"base64");
            byte[] buf = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len;
            while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
            }
            FileOutputStream fout = new FileOutputStream(screenshotFile);
            out.writeTo(fout);
            fout.close();
            out.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        Map<String, String> thisScreenShot = new HashMap<String, String>();
        thisScreenShot.put("image", "image" + (imageCount-1) + ".png");
        thisScreenShot.put("description", description);
        ((SimpleSequence) testModel.get("screenshots")).add(thisScreenShot);

    }

    public void close() {		
        File summary = new File(reportsDir, "index.html");
        Writer out = null;
        try {
            Template temp = cfg.getTemplate(SUMMARY_TEMPLATE);        
            out = new FileWriter(summary);
            temp.process(reportModel, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally { 
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
