package pl.nask.crs.commons.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.pattern.PatternConverter;
import org.apache.log4j.rolling.*;
import org.apache.log4j.rolling.helper.Action;
import org.apache.log4j.rolling.helper.FileRenameAction;
import org.apache.log4j.rolling.helper.GZCompressAction;
import org.apache.log4j.rolling.helper.ZipCompressAction;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.util.Date;

/**
 * TimeBaseRollingPolicy with initTime
 */
public class CustomRollingPolicy extends RollingPolicyBase implements TriggeringPolicy {

    private long initTime;

    /**
     * Time for next determination if time for rollover.
     */
    private long nextCheck = 0;

    /**
     * File name at last rollover.
     */
    private String lastFileName = null;

    /**
     * Length of any file type suffix (.gz, .zip).
     */
    private int suffixLength = 0;

    /**
     * Constructs a new instance.
     */

    public CustomRollingPolicy() {
        initTime = System.currentTimeMillis();
    }

    /**
     * Prepares instance of use.
     */
    public void activateOptions() {
        super.activateOptions();

        PatternConverter dtc = getDatePatternConverter();

        if (dtc == null) {
            throw new IllegalStateException(
                    "FileNamePattern [" + getFileNamePattern()
                            + "] does not contain a valid date format specifier");
        }

        StringBuffer buf = new StringBuffer();
        formatFileName(new Date(initTime), buf);
        lastFileName = buf.toString();

        suffixLength = 0;

        if (lastFileName.endsWith(".gz")) {
            suffixLength = 3;
        } else if (lastFileName.endsWith(".zip")) {
            suffixLength = 4;
        }
    }

    /**
     * {@inheritDoc}
     */
    public RolloverDescription initialize(
            final String currentActiveFile, final boolean append) {
        nextCheck = ((initTime / 1000) + 1) * 1000;

        StringBuffer buf = new StringBuffer();
        formatFileName(new Date(initTime), buf);
        lastFileName = buf.toString();

        //
        //  RollingPolicyBase.activeFileName duplicates RollingFileAppender.file
        //    and should be removed.
        //
        if (activeFileName != null) {
            return new RolloverDescriptionImpl(activeFileName, append, null, null);
        } else if (currentActiveFile != null) {
            return new RolloverDescriptionImpl(
                    currentActiveFile, append, null, null);
        } else {
            return new RolloverDescriptionImpl(
                    lastFileName.substring(0, lastFileName.length() - suffixLength), append,
                    null, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public RolloverDescription rollover(final String currentActiveFile) {
        long n = System.currentTimeMillis();
        nextCheck = ((n / 1000) + 1) * 1000;

        StringBuffer buf = new StringBuffer();
        formatFileName(new Date(n), buf);

        String newFileName = buf.toString();

        //
        //  if file names haven't changed, no rollover
        //
        if (newFileName.equals(lastFileName)) {
            return null;
        }

        Action renameAction = null;
        Action compressAction = null;
        String lastBaseName =
                lastFileName.substring(0, lastFileName.length() - suffixLength);
        String nextActiveFile =
                newFileName.substring(0, newFileName.length() - suffixLength);

        //
        //   if currentActiveFile is not lastBaseName then
        //        active file name is not following file pattern
        //        and requires a rename plus maintaining the same name
        if (!currentActiveFile.equals(lastBaseName)) {
            renameAction =
                    new FileRenameAction(
                            new File(currentActiveFile), new File(lastBaseName), true);
            nextActiveFile = currentActiveFile;
        }

        if (suffixLength == 3) {
            compressAction =
                    new GZCompressAction(
                            new File(lastBaseName), new File(lastFileName), true);
        }

        if (suffixLength == 4) {
            compressAction =
                    new ZipCompressAction(
                            new File(lastBaseName), new File(lastFileName), true);
        }

        lastFileName = newFileName;
        initTime = n;

        return new RolloverDescriptionImpl(
                nextActiveFile, false, renameAction, compressAction);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTriggeringEvent(
            final Appender appender, final LoggingEvent event, final String filename,
            final long fileLength) {
        return System.currentTimeMillis() >= nextCheck;
    }
}
