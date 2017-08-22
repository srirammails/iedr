package pl.nask.crs.commons.config;

import java.util.Date;

/**
 * Used to separate read/write locations for invoices
 * read location (shared) : archiveFileConfig
 * read/write location (local) : fileConfig
 *
 */
public class InvoiceExportConfiguration extends ExportConfiguration {

    private String archiveRootDir;

    public InvoiceExportConfiguration(String outputRootDir, String archiveRootDir, boolean useDateSubdir) {
        super(outputRootDir, useDateSubdir);
        this.archiveRootDir = archiveRootDir;
    }

    public TargetFileInfo archiveFileConfig(String fileName, Date date) {
        return new TargetFileInfo (directoryFor(archiveRootDir, useDateSubdir, date), fileName);
    }

}
