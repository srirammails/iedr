package pl.nask.crs.api.common;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServerTime {	
	private Calendar currentTime;

	public ServerTime() {
		currentTime = Calendar.getInstance();
	}
	
	@XmlAttribute(name="hours")
	public int getHours() {
		return currentTime.get(Calendar.HOUR_OF_DAY);
	}
	
	@XmlAttribute(name="seconds")
	public int getSeconds() {
		return currentTime.get(Calendar.SECOND);
	}
	
	@XmlAttribute(name="minutes")
	public int getMinutes() {
		return currentTime.get(Calendar.MINUTE);
	}
}
