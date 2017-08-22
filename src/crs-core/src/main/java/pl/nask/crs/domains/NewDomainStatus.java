package pl.nask.crs.domains;

/**
 * Used as a request to change the status of the domain
 * @author Artur Gniadzik
 *
 */
public enum NewDomainStatus {
	Active, // activate an inactive domain / run triple pass on a domain 
	Deleted, // enter voluntary NRP 
	Reactivate // exit voluntary NRP
}
