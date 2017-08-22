package pl.nask.crs.web.displaytag;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.MessageFormatColumnDecorator;
import org.displaytag.properties.MediaTypeEnum;

public class StrutsMessageFormatDecorator implements DisplaytagColumnDecorator {
	/**
     * Logger.
     */
    private static Log log = LogFactory.getLog(MessageFormatColumnDecorator.class);

    /**
     * Pre-compiled messageFormat.
     */
    private MessageFormat format;

    
    public StrutsMessageFormatDecorator(String bundleName, String bundleKeyName, Locale locale) {		
		ResourceBundle rb = ResourceBundle.getBundle(bundleName);
		String propertyValue = rb.getString(bundleKeyName);
		this.format = new MessageFormat(propertyValue, locale);
	} 
    
    public StrutsMessageFormatDecorator(String bundleName, String bundleKeyName) {
    	this(bundleName, bundleKeyName, Locale.getDefault());
	} 

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
    {
        try
        {
            return this.format.format(new Object[]{columnValue});
        }
        catch (IllegalArgumentException e)
        {
            log.error(Messages.getString("MessageFormatColumnDecorator.invalidArgument", new Object[]{ //$NON-NLS-1$
                this.format.toPattern(), columnValue != null ? columnValue.getClass().getName() : "null"})); //$NON-NLS-1$

            return columnValue;
        }
    }
}
