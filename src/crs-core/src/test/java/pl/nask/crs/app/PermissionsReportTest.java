package pl.nask.crs.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.config.VersionInfo;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionGroup;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

/**
 * Generates report about the configured permissions. 
 * Uses permissionDescription.properties!
 * 
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/application-services-config.xml"})
public class PermissionsReportTest extends AbstractTestNGSpringContextTests {	
	@Resource
	AuthorizationGroupsFactory authGroupFactory;
	
	PrintStream out;
	
	@Test
	public void generateReport() throws FileNotFoundException {
		try {
			List<Group> groups = authGroupFactory.getAllGroups();
			out = new PrintStream(new File("PermissionsReport.txt"));
			printApplicationVersion();
			printGroups(groups);
			printPermissions(groups);
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	
	private void printApplicationVersion() {
		print("Application version info");
		TableFormatter f = new TableFormatter(Locale.ENGLISH);
		f.addColumn("Name", 20, TableFormatter.leftAlignedStringFormat(20), true);
		f.addColumn("Value", 25, TableFormatter.leftAlignedStringFormat(25), true);
		f.addDataLine(new Object[] {"API Commands Version", VersionInfo.getApiCommmandsVersion()});
		f.addDataLine(new Object[] {"API Version", VersionInfo.getApiVersion()});
		f.addDataLine(new Object[] {"CRS API (WS) Version", VersionInfo.getCrsApiVersion()});
		f.addDataLine(new Object[] {"CRS-WEB Version", VersionInfo.getCrsVersion()});
		f.addDataLine(new Object[] {"SVN Revision", VersionInfo.getRevision()});
		print(f);
	}


	private void printPermissions(List<Group> groups) {
		print("Permission configuration");
		for (Group g: groups) {
			TableFormatter tf = groupFormatter();
			addGroupInfo(tf, g);
			print(tf);
			TableFormatter formatter = permissionsFormatter();
			addPermissionsInfo(formatter, g.getPermissions());
			print(formatter);
		}
	}


	private void print(Object tf) {
		String msg = tf.toString();
		out.println(msg);
		System.out.println(msg);
	}


	private void addPermissionsInfo(TableFormatter formatter, Collection<Permission> permissions) {
		for (Permission p: permissions) {
			if (p instanceof PermissionGroup) {
				addPermissionsInfo(formatter, ((PermissionGroup) p).getEmbeddedPermissions());
			} else {
				addPermissionInfo(formatter, p);
			}
		}
	}

	private String descriptionFor(Permission p) {
		return p.getDescription();
	}

	private void printGroups(List<Group> groups) {
		print("Configured groups");
		TableFormatter tableFormatter = groupFormatter();
		for (Group g: groups) {
			addGroupInfo(tableFormatter, g);
		}
		print(tableFormatter);
	}


	private void addGroupInfo(TableFormatter tableFormatter, Group g) {
		tableFormatter.addDataLine(new Object[]{g.getLevel().getLevel(), g.getLabel()});
	}

	private void addPermissionInfo(TableFormatter formatter, Permission p) {
		formatter.addDataLine(new Object[] {p.getId(), p.getClass().getSimpleName(), descriptionFor(p)});
	}

	private TableFormatter groupFormatter() {
		TableFormatter tableFormatter = new TableFormatter(Locale.ENGLISH);
		tableFormatter.addColumn("NH_Level", 10, TableFormatter.leftAlignedStringFormat(10), true);
		tableFormatter.addColumn("Group name", 40, TableFormatter.leftAlignedStringFormat(40), true);
		return tableFormatter;
	}

	private TableFormatter permissionsFormatter() {
		TableFormatter f = new TableFormatter(Locale.ENGLISH);
		f.addColumn("Permission ID", 40, TableFormatter.leftAlignedStringFormat(40), true);
		f.addColumn("Permission type", 25, TableFormatter.leftAlignedStringFormat(25), true);
		f.addColumn("Description", 100, TableFormatter.leftAlignedStringFormat(100), true);
		return f;
	}

}
