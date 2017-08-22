package pl.nask.crs.commons.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;

import pl.nask.crs.commons.Pair;

public final class DateUtils {
	
	private DateUtils() {}

	
	/**
	 * Returns the date representing the end-of-day (dd-MM-yyyy:23:59:59:999)of the date supplied with the parameter. Returns null if null provides as a parameter
	 * 
	 * @param date
	 * @return
	 */
	public static Date endOfDay(Date date) {
		if (date == null)
			return null;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return  c.getTime();
	}
	
	/**
	 * Returns the midnight for the day from the date supplied with the parameter, null if the param is null
	 * 
	 * @param date
	 * @return
	 */
	public static Date startOfDay(Date date) {
		if (date == null)
			return null;
	
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

    /**
     * Returns first day of month from date supplied with the parameter, null if the param is null
     *
     * @param date
     * @return
     */
    public static Date startOfMonth(Date date) {
        if (date == null)
            return null;

        Calendar res = Calendar.getInstance();
        res.setTime(date);
        res.set(Calendar.DAY_OF_MONTH, 1);
        res.set(Calendar.HOUR_OF_DAY, 0);
        res.set(Calendar.MINUTE, 0);
        res.set(Calendar.SECOND, 0);
        res.set(Calendar.MILLISECOND, 0);
        return res.getTime();
    }

    /**
     * Returns last day of month from date supplied with the parameter, null if the param is null
     *
     * @param date
     * @return
     */
    public static Date endOfMonth(Date date) {
        if (date == null)
            return null;

        Calendar res = Calendar.getInstance();
        res.setTime(date);
        res.set(Calendar.DAY_OF_MONTH, res.getActualMaximum(Calendar.DAY_OF_MONTH));
        res.set(Calendar.HOUR_OF_DAY, 23);
        res.set(Calendar.MINUTE, 59);
        res.set(Calendar.SECOND, 59);
        res.set(Calendar.MILLISECOND, 999);
        return res.getTime();
    }

    public static Date startOfYear(Date date) {
        if (date == null)
            return null;

        Calendar res = Calendar.getInstance();
        res.setTime(date);
        res.set(Calendar.MONTH, 0);
        res.set(Calendar.DAY_OF_MONTH, 1);
        res.set(Calendar.HOUR_OF_DAY, 0);
        res.set(Calendar.MINUTE, 0);
        res.set(Calendar.SECOND, 0);
        res.set(Calendar.MILLISECOND, 0);
        return res.getTime();
    }

    public static Date endOfYear(Date date) {
        if (date == null)
            return null;

        Calendar res = Calendar.getInstance();
        res.setTime(date);
        res.set(Calendar.DAY_OF_YEAR, res.getActualMaximum(Calendar.DAY_OF_YEAR));
        res.set(Calendar.HOUR_OF_DAY, 23);
        res.set(Calendar.MINUTE, 59);
        res.set(Calendar.SECOND, 59);
        res.set(Calendar.MILLISECOND, 999);
        return res.getTime();
    }

    /**
     * Return credit card exp date in realex specific format
     * @param cardExpDate
     * @return
     */
    public static String getCardExpDateAsString(XMLGregorianCalendar cardExpDate) {
        String formatedMonth = null;
        String formatedYear = null;
        if (cardExpDate.getMonth() < 10) {
            formatedMonth = "0" + cardExpDate.getMonth();
        } else {
            formatedMonth = Integer.toString(cardExpDate.getMonth());
        }
        formatedYear = Integer.toString(cardExpDate.getYear()).substring(2);
        return formatedMonth + formatedYear;
    }

    /**
     * Return credit card exp date in realex specific format
     * @param cardExpDate
     * @return
     */
    public static String getCardExpDateAsString(Date cardExpDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMyy");
        return sdf.format(cardExpDate);
    }

    /**
     *
     * @param yearMonth in format : yyyy-mm
     * @return
     */
    public static Pair<Date, Date> getFirstLastDayOfMonthDatePair(String yearMonth) {
        if (yearMonth == null || yearMonth.trim().length() == 0)
            throw new IllegalArgumentException();
        String[] strings = yearMonth.split("-");
        if (strings.length != 2)
            throw new IllegalArgumentException();
        Integer year = Integer.valueOf(strings[0]);
        Integer month = Integer.valueOf(strings[1]) - 1;
        if (month < 0 || month > 11)
            throw new IllegalArgumentException();
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        Date firstDayOfMonthDate = startOfMonth(c.getTime());
        Date lastDayOfMonthDate = endOfMonth(c.getTime());
        return new Pair<Date, Date>(firstDayOfMonthDate, lastDayOfMonthDate);
    }

    public static XMLGregorianCalendar asXmlGregorianCalendar(Date date) {
        if (date == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    BigInteger.valueOf(cal.get(Calendar.YEAR)),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND),
                    null,
                    DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns currentdate +- days
     * @param days
     * @return
     */
    public static Date getCurrDate(int days) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    
    public static boolean isAlmostSameTime(Date d1, Date d2, int maxDifferenceSeconds) {
    	long time1 = d1.getTime() / 1000;
    	long time2 = d2.getTime() / 1000;
    	long diff = Math.abs(time1 - time2);
    	return diff < maxDifferenceSeconds;
    }


	public static boolean isDateBetween(Date query, Date from, Date to, boolean leftInclusive, boolean rightInclusive) {
		if (from == null && to == null)
			return true;
		if (from == null)
			return isBefore(query, to, rightInclusive);
		if (to == null)
			return isBefore(from, query, leftInclusive);
		
		return isBefore(query, to, rightInclusive)
				&&		
			isBefore(from, query, leftInclusive);			
	}


	public static boolean isBefore(Date query, Date limit, boolean inclusive) {
		Date queriedDay = startOfDay(query);
		Date limitDay = startOfDay(limit);
		if (inclusive && queriedDay.getTime() == limitDay.getTime())
			return true;
		
		if (!inclusive && queriedDay.getTime() < limitDay.getTime())
			return true;
		
		return false;
	}


	/**
	 * returns an absolute difference between two dates, in days
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffInDays(Date date1, Date date2) {
		return Math.abs(
				Days.daysBetween(
						new DateTime(date1).toDateMidnight(), 
						new DateTime(date2).toDateMidnight())
						.getDays());
	}
}
