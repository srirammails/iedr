package pl.nask.crs.commons.config;

import java.util.List;

public interface ApplicationConfig {
    Long getIedrAccountId();

    Long getGuestAccountId();

    NRPConfig getNRPConfig();

    int getEligibleForDeletionDomainState();
    
    ExportConfiguration getAccountUpdateExportConfig();
    
    ExportConfiguration getDoaExportConfiguration();
    
    List<Integer> getRenewalNotificationPeriods();
    
    List<Integer> getSuspensionNotificationPeriods();

    /**
     * default period for GIBO registrations, in years
     * @return
     */
	int getGiboDefaultPeriod();

	/**
	 * retry timeout for GIBO domains, in hours
	 * @return
	 */
	int getGiboRetryTimeout();
	
	/**
	 * a period in which the ticket has to be processed or will be deleted from the system
	 * 
	 * @return
	 */
	int getTicketExpirationPeriod();

	ExportConfiguration getXmlInvoiceExportConfig();

	ExportConfiguration getPdfInvoiceExportConfig();

    ExportConfiguration getABMXmlInvoiceExportConfig();
    
    /**
     * 
     * 
     * @return
     * 		maximum number of days the user password may be used without changing.
     */
    int getPasswordExpiryPeriod();

    /**
     * @return session timeout in minutes
     */
	int getUserSessionTimeout();

    LoginLockoutConfig getLoginLockoutConfig();

    GAConfig getGoogleAuthenticationConfig();
    
    /**
     * @return
     * password token expiry time, in minutes
     */
	int getPasswordResetTokenExpiry();

	IncomingDocsConfig getIncomingDocsConfig();

    /**
     * @return Minimal count of nameservers that must be associated with a domain or a ticket
     */
    int getNameserverMinCount();

    /**
     * @return Maximum count of nameservers that can be associated with a domain or a ticket
     */
    int getNameserverMaxCount();

    /**
     * A perion after which a generated authode is cleared
     */
    int getAuthCodeExpirationPeriod();

    /**
     * Number of trials that may be made while entering an authcode
     */
    int getAuthCodeFailureLimit();

    /**
     * Number of allowed daily generations from Portal
     */
    int getAuthCodePortalLimit();

    /**
     * @return maximum size in bytes of uploaded documents
     */
    int getDocumentUploadSizeLimit();

    /**
     * @return
     */
    int getDocumentUploadCountLimit();

    List<String> getDocumentAllowedTypes();
}
