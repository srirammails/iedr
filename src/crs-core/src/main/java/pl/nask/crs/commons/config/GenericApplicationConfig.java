package pl.nask.crs.commons.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Application config based on the configuration provided by the GenericConfig
 * @author Artur Gniadzik
 *
 */
public class GenericApplicationConfig implements ApplicationConfig {

	private GenericConfig baseConfig;

	public GenericApplicationConfig(GenericConfig baseConfig) {
		this.baseConfig = baseConfig;
	}
	
	@Override
	public Long getIedrAccountId() {	
		return getLong("iedr_account_id");
	}
	
	@Override
    public Long getGuestAccountId() {
    	return getLong("guest_account_id"); 
    }

	@Override
	public NRPConfig getNRPConfig() {
		return new NRPConfig(baseConfig);
	}

    @Override
    public int getEligibleForDeletionDomainState() {
        return getInt("deletion_dsm_state");
    }

	@Override
	public ExportConfiguration getAccountUpdateExportConfig() {
		return new ExportConfiguration(
    			getString("account_update_xml_output_dir"), 
    			getBool("account_update_xml_output_use_date_subdir"));
	}
	@Override
	public ExportConfiguration getDoaExportConfiguration() {	
		return new ExportConfiguration(
    			getString("doa_xml_output_dir"), 
    			getBool("doa_xml_output_use_date_subdir"));
	}
	
    @Override
    public List<Integer> getRenewalNotificationPeriods() {
        String periodString = getString("renewal_notification_periods");
        return fromString(periodString);
    }

    @Override
    public List<Integer> getSuspensionNotificationPeriods() {
        String periodString = getString("suspension_notification_periods");
        return fromString(periodString);
    }
    
    @Override
    public int getGiboDefaultPeriod() {
    	return getInt("gibo_default_period");    
    }

    @Override
    public int getGiboRetryTimeout() {
    	return getInt("gibo_retry_timeout_hours");
    }
    
    @Override
    public int getTicketExpirationPeriod() {
    	return getInt("ticket_expiration_period");
    }
    
    @Override
    public ExportConfiguration getXmlInvoiceExportConfig() {
    	return new InvoiceExportConfiguration(
    			getString("xml_invoice_output_dir"),
                getString("xml_invoice_archive_dir"),
    			getBool("xml_invoice_output_use_date_subdir"));
    }
    
    @Override
    public ExportConfiguration getPdfInvoiceExportConfig() {
    	return new InvoiceExportConfiguration(
    		getString("pdf_invoice_output_dir"),
            getString("pdf_invoice_archive_dir"),
    		getBool("pdf_invoice_output_use_date_subdir"));
    }

    @Override
    public ExportConfiguration getABMXmlInvoiceExportConfig() {
        return new ExportConfiguration(
                getString("abm_xml_invoice_output_dir"),
                getBool("abm_xml_invoice_output_use_date_subdir"));
    }
    
    @Override
    public int getPasswordExpiryPeriod() {
    	return getInt("password_expiry_period");
    }
    
    @Override
    public int getUserSessionTimeout() {    
    	return getInt("user_session_timeout_minutes");
    }

    @Override
    public LoginLockoutConfig getLoginLockoutConfig() {
        return new LoginLockoutConfig(baseConfig);
    }
    
    @Override
    public int getPasswordResetTokenExpiry() {    
    	return getInt("password_reset_token_expiry_period");
    }

    @Override
    public GAConfig getGoogleAuthenticationConfig() {
        return new GAConfig(baseConfig);
    }

    @Override
    public IncomingDocsConfig getIncomingDocsConfig() {    
        IncomingDocsConfig cfg = new IncomingDocsConfig();
        String[] mappingKeys = new String[] {
                "FAX_NEW", "FAX_ASSIGNED", "ATTACHMENT_NEW", "ATTACHMENT_ASSIGNED", "PAPER_NEW", "PAPER_ASSIGNED"
        };

        for (String key: mappingKeys) {
            String configKey = "incoming_docs_path_" + key.toLowerCase();
            if (baseConfig.getEntry(configKey) != null) {
                cfg.addMapping(key, getString(configKey));
            }
        }

        return cfg;
    }

    @Override
    public int getNameserverMinCount() {
        return getInt("nameserver_min_count");
    }

    @Override
    public int getNameserverMaxCount() {
        return getInt("nameserver_max_count");
    }

    @Override
    public int getAuthCodeExpirationPeriod() {
        return getInt("authcode_expiration_period");
    }

    @Override
    public int getAuthCodeFailureLimit() {
        return getInt("authcode_failure_limit");
    }

    @Override
    public int getAuthCodePortalLimit() {
        return getInt("authcode_portal_limit");
    }

    @Override
    public int getDocumentUploadSizeLimit() {
        return getInt("document_size_limit_in_mb") * 1024 * 1024;
    }

    @Override
    public int getDocumentUploadCountLimit() {
        return getInt("document_count_limit");
    }

    @Override
    public List<String> getDocumentAllowedTypes() {
        String allowedTypes = getString("document_allowed_types");
        List<String> result = new ArrayList<String>();
        if (allowedTypes != null && !allowedTypes.isEmpty()) {
            String[] types = allowedTypes.split(",");
            for (int i = 0; i < types.length; i++) {
                result.add(types[i].toLowerCase());
            }
        }
        return result;
    }

    /*
     * convenience methods 
     */
    
    private int getInt(String configKey) {
    	return (Integer) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }
    
    private long getLong(String configKey) {
    	return (Long) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }
    
    private String getString(String configKey) {
    	return baseConfig.getEntryOrThrowNpe(configKey).getValue();
    }
    
    private boolean getBool(String configKey) {
    	return (Boolean) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }

    private List<Integer> fromString(String periodsAsString) {
        List<Integer> periods = new ArrayList<Integer>();
        for (String period : periodsAsString.split(",")) {
            periods.add(Integer.parseInt(period));
        }
        return periods;
    }        
}
