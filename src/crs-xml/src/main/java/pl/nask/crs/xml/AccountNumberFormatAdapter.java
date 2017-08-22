package pl.nask.crs.xml;

import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Formats account number adding a required number of leading '0'
 * 
 * @author Artur Gniadzik
 *
 */
public class AccountNumberFormatAdapter extends XmlAdapter<String, BigInteger> {
	private static final String[] prefix = {
		"00000000", 
		"0000000",
		"000000",
		"00000",
		"0000",
		"000",
		"00",
		"0"};
	
	@Override
	public String marshal(BigInteger v) throws Exception {
		String n = v.toString();
		int len = n.length();
		if (len < 8)
			return prefix[len] + v;
		else 
			return n;
	}

	@Override
	public BigInteger unmarshal(String v) throws Exception {
		return new BigInteger(v);
	}

}
