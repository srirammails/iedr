package pl.nask.crs.commons.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class TableFormatterTest {
	@Test
	public void testFormat() {
		TableFormatter formatter = new TableFormatter(Locale.ENGLISH);
		initTable(formatter);
		for (int i=0; i< 10; i++) {
			formatter.addDataLine(new Object[]{
            		 "domain.ie",
            		 "Domain holder name",
            		 1,
            		 "Active",
            		 "OpType",
            		 new Date(),
            		 new Date(),
            		 new BigDecimal(11.1100f)
            		 
             });
		}
		
		System.out.println(formatter);
	}
	
	@Test
	public void testFormatHtml() {
		TableFormatter formatter = new TableFormatter(Locale.ENGLISH);
		initTable(formatter);
		for (int i=0; i< 10; i++) {
			formatter.addDataLine(new Object[]{
            		 "domain.ie",
            		 "Domain holder name",
            		 1,
            		 "Active",
            		 "OpType",
            		 new Date(),
            		 new Date(),
            		 new BigDecimal(11.1100f)
            		 
             });
		}
		
		System.out.println(formatter.toHtmlString());
	}
		   
	private void initTable(TableFormatter tableFormatter) {
		tableFormatter.addColumn("Domain", 60, TableFormatter.leftAlignedStringFormat(60), true);
		tableFormatter.addColumn("Holder", 60, TableFormatter.leftAlignedStringFormat(60), true);
		tableFormatter.addColumn("Years", 5, TableFormatter.digitsFormat(5), false);
		tableFormatter.addColumn("NRP", 10, TableFormatter.rightAlignedStringFormat(10), true);
		tableFormatter.addColumn("Type", 12, TableFormatter.rightAlignedStringFormat(12), true);
		tableFormatter.addColumn("RegDt", 10, TableFormatter.dateYMDFormat(), true);
		tableFormatter.addColumn("RenDt", 10, TableFormatter.dateYMDFormat(), true);				
		tableFormatter.addColumn("Value", 10, TableFormatter.moneyFormat(10), true);
	}
}