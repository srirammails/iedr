package pl.nask.crs.domains;

import pl.nask.crs.statuses.Status;

/**
 * @author Kasia Fulara
 */
public interface BillingStatus extends Status {

    String getDetailedDescription();

    String getColour();

	boolean isMSD();
}