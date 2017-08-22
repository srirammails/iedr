package pl.nask.crs.web.displaytag;

import org.apache.log4j.Logger;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;


/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class BooleanDecorator implements DisplaytagColumnDecorator {

    private static final Logger LOG = Logger.getLogger(BooleanDecorator.class);

    @Override
    public Object decorate(Object o, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {
        if (o instanceof Boolean) {
            return (Boolean) o ? "YES" : "NO";
        } else {
            LOG.warn("Value is not boolean type: " + o);
            return o;
        }
    }
}
