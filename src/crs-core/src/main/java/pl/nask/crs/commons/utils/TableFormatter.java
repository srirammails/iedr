package pl.nask.crs.commons.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TableFormatter implements HtmlPrintable {
	public static ColumnFormat leftAlignedStringFormat(int size) {
		return new ColumnFormat("%-" + size + "s");
	}
	
	public static ColumnFormat rightAlignedStringFormat(int size) {
		return new ColumnFormat("%" + size + "s", true);
	}
	
	public static ColumnFormat dateYMDFormat() {
		return new ColumnFormat("%1$tY-%1$tm-%1$td");
	}
	
	public static ColumnFormat digitsFormat(int size) {
		return new ColumnFormat("%" + size + "d", true);
	}
	
	public static ColumnFormat moneyFormat(int digits) {
		return new ColumnFormat("%" + digits + ".2f", true);
	}
	
	List<Column> columns = new ArrayList<Column>();
	List<Object[]> data = new LinkedList<Object[]>();
	private Locale locale;
	
	public TableFormatter(Locale locale) {
		this.locale = locale;
	}
	
	public void addColumn(String name, int size, ColumnFormat formatting, boolean visible) {
		this.columns.add(new Column(name, size, formatting, visible));		
	}

	
	
	public void addDataLine(Object[] objectArray) {
		data.add(objectArray);
	}
	
	private void printData(StringBuilder sb, boolean html) {
		for (Object[] objectArray: data) {
			if(html) {
				sb.append("<tr>");
			} else {
				sb.append("|");
			}
			for (int i=0; i<objectArray.length; i++) {
				Column c = columns.get(i);
				if (c.isVisible()) {
					sb.append(c.getFormattedData(objectArray[i], html));
					if (!html) {
						sb.append("|");
					}
				}
			}
			if (html) {
                sb.append("</tr>");
            } else {
                sb.append("\n");
            }
		}
	}

	@Override
	public String toString() {	
		return makeString(false);
	}
	
	private String makeString(boolean html) {
		StringBuilder sb = new StringBuilder(data.size() * columns.size() * 30 + 500);
		openTable(sb, html);
		tableHeader(sb, html);
		printData(sb, html);
		closeTable(sb, html);
		
		return sb.toString();
	}

	private void closeTable(StringBuilder sb, boolean html) {
		if (html) {
			sb.append("</table>");
		} else {
			lineSeparator(sb);
		}
	}

	private void openTable(StringBuilder sb, boolean html) {
		if (html) {
			sb.append("<table border='1' cellpadding='2'>");
		} else {
			lineSeparator(sb);
		}
	}

	@Override
	public String toHtmlString() {
		return makeString(true);
	}
	
	
	private void tableHeader(StringBuilder sb, boolean html) {
		if (html) {
			sb.append("<tr>");
		} else {
			sb.append("|");
		}
		for (Column c: columns) {
			if (c.isVisible()) {
				sb.append(c.getFormattedHeader(html));
				if (!html) {
					sb.append("|");
				}
			}
		}
		if (html) {
			sb.append("</tr>");
		} else {
			sb.append("\n");
			lineSeparator(sb);
		}
	}

	private void lineSeparator(StringBuilder sb) {
		sb.append("|");
		for (Column c: columns) {
			if (c.isVisible()) {
				for (int i=0; i < c.getSize(); i++) {
					sb.append("-");
				}
				sb.append("|");
			}
		}
		sb.append("\n");
	}
	
	private static class ColumnFormat {

		private boolean rightAligned;
		private String formatting;

		public ColumnFormat(String formatting) {
			this.formatting = formatting;
		}

		public ColumnFormat(String formatting, boolean rightAligned) {
			this.formatting = formatting;
			this.rightAligned = rightAligned;
		}
	
	}

	private class Column {
		private String name;
		private int size;
		private ColumnFormat formatting;
		private boolean visible;
		
		public Column(String name, int size, ColumnFormat formatting, boolean visible) {
			this.name = name;
			this.size = size;
			this.formatting = formatting;
			this.visible = visible;
		}

		public int getSize() {
			return size;
		}

		public String getFormattedData(Object object, boolean html) {			
			try {
				if (html) {
					if (object == null) {
						return "<td>&nbspl</td>";
					} else {
						return "<td" + (formatting.rightAligned ? " align=\"right\">" : ">") 
						+ String.format(locale, formatting.formatting, object).trim() + "</td>";
					}
				} else {
					if (object == null) {
						return String.format("%" + size + "s", "");
					} else {
						return String.format(locale, formatting.formatting, object);
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Cannot format " + object + " using format " + formatting, e);
			}
		}
		
		public String getFormattedHeader(boolean html) {
			if (html) {
				return "<th>" + name + "</th>";
			} else {
				return String.format("%-" + size + "s", name);
			}
		}

		public boolean isVisible() {			
			return visible;
		}
	}
}
