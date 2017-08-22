package pl.nask.crs.xml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return null;
        } else {
            SimpleDateFormat sdf = makeSimpleDateFormat();
            return sdf.format(v);
        }
    }

    private SimpleDateFormat makeSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        } else {
            SimpleDateFormat sdf = makeSimpleDateFormat();
            return sdf.parse(v);
        }
    }

}
