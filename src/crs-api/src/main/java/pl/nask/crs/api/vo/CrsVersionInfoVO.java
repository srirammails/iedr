package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.config.VersionInfo;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CrsVersionInfoVO {
	
	public CrsVersionInfoVO() {
	}
	
	@XmlAttribute(name="versionNumber")
	public String getVersionNumber() {
		return VersionInfo.getCrsApiVersion();
	}
	
	@XmlAttribute(name="revisionNumber")
	public String getRevisionNumber() {
		return VersionInfo.getRevision();
	}
	
	@XmlAttribute(name="profileName")
	public String getProfileName() {
		return VersionInfo.getBuildProfile();
	}
	
	@XmlAttribute(name="formattedVersion")
	public String getFormattedVersion() {
		return String.format("CRS-WS Version %s - %s r %s", getVersionNumber(), getProfileName(), getRevisionNumber());
	}
}
