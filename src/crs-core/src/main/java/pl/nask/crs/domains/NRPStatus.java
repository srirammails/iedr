package pl.nask.crs.domains;

public enum NRPStatus implements DSMStatus {
	Active("A", "Active"),
	InvoluntaryMailed("IM", "Involuntary Mailed"),
	VoluntaryMailed("VM", "Voluntary Mailed"),
	InvoluntarySuspended("IS", "Involuntary Suspended"),
	VoluntarySuspended("VS", "Voluntary Suspended"),
	Deleted("D", "Deleted"),
	PostTransactionAudit("P", "Post-Transaction Audit"),
	TransactionFailed("T", "Transaction Failed"), 
	TransferPendingActive("XPA", "Transfer Pending - Active", "Transfer pending"),
	TransferPendingInvNrp("XPI", "Transfer Pending - Inv. NRP", "Transfer pending"),
	TransferPendingVolNRP("XPV", "Transfer Pending - Voluntary NRP", "Transfer pending"),
	InvoluntaryMailedPaymentPending("IMPP", "Involuntary Mailed Payment Pending"), 
	InvoluntarySuspendedPaymentPending("ISPP", "Involuntary Suspended Payment Pending"),
	NA("N/A", "N/A");
	
//	XPA = Transfer Pending - Active, XPI = Transfer Pending - Inv. NRP, XPV = Transfer Pending - Voluntary NRP
	
	private final String description;
	private final String shortDescription;
	private final String code;

	private NRPStatus(String code, String description) {
		this(code, description, description);		
	}
	
	private NRPStatus(String code, String description, String shortDescription) {
		this.code = code;
		this.description = description;
		this.shortDescription = shortDescription;
	}
	public static NRPStatus forCode(String code) {
		if ("A".equals(code)) {
			return Active;
		} else if ("IM".equals(code)) {
			return InvoluntaryMailed;
		} else if ("VM".equals(code)) {
			return VoluntaryMailed;
		} else if ("VS".equals(code)) {
			return VoluntarySuspended;
		} else if ("IS".equals(code)) {
			return InvoluntarySuspended;
		} else if ("D".equals(code)) {
			return Deleted;
		} else if ("P".equals(code)) {
			return PostTransactionAudit;
		} else if ("T".equals(code)) {
			return TransactionFailed;
		} else if ("XPA".equals(code)) {
			return TransferPendingActive;
		} else if ("XPI".equals(code)) {
			return TransferPendingInvNrp;
		} else if ("XPV".equals(code)) {
			return TransferPendingVolNRP;		
		} else if ("IMPP".equals(code)) {
			return InvoluntaryMailedPaymentPending;
		} else if ("ISPP".equals(code)) {
			return InvoluntarySuspendedPaymentPending;
		} else if (code == null || code.trim().length() == 0) {
			return null;
		} else {
			throw new IllegalArgumentException("Unsupported code for NRPStatus: " + code);
		}
		
	}

    public static NRPStatus forName(String name) {
        if ("Active".equalsIgnoreCase(name)) {
            return Active;
        } else if ("InvoluntaryMailed".equalsIgnoreCase(name)) {
            return InvoluntaryMailed;
        } else if ("VoluntaryMailed".equalsIgnoreCase(name)) {
            return VoluntaryMailed;
        } else if ("VoluntarySuspended".equalsIgnoreCase(name)) {
            return VoluntarySuspended;
        } else if ("InvoluntarySuspended".equalsIgnoreCase(name)) {
            return InvoluntarySuspended;
        } else if ("Deleted".equalsIgnoreCase(name)) {
            return Deleted;
        } else if ("PostTransactionAudit".equalsIgnoreCase(name)) {
            return PostTransactionAudit;
        } else if ("TransactionFailed".equalsIgnoreCase(name)) {
            return TransactionFailed;
        } else if ("TransferPendingActive".equalsIgnoreCase(name)) {
            return TransferPendingActive;
        } else if ("TransferPendingInvNrp".equalsIgnoreCase(name)) {
            return TransferPendingInvNrp;
        } else if ("TransferPendingVolNRP".equalsIgnoreCase(name)) {
            return TransferPendingVolNRP;
        } else if ("InvoluntaryMailedPaymentPending".equalsIgnoreCase(name)) {
            return InvoluntaryMailedPaymentPending;
        } else if ("InvoluntarySuspendedPaymentPending".equalsIgnoreCase(name)) {
            return InvoluntarySuspendedPaymentPending;
        } else if (name == null || name.trim().length() == 0) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported name for NRPStatus: " + name);
        }

    }

    @Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	public boolean isNRP() {
		switch (this) {
		case Deleted:
		case InvoluntaryMailed:
		case InvoluntarySuspended:
		case VoluntaryMailed:
		case VoluntarySuspended:
		case TransferPendingVolNRP:
		case TransferPendingInvNrp:
		case InvoluntaryMailedPaymentPending:
		case InvoluntarySuspendedPaymentPending:
			return true;
		default: 
			return false;
		}
	}

	public boolean isVoluntaryNRP() {
		return this == VoluntaryMailed || this == VoluntarySuspended || this == TransferPendingVolNRP;
	}

	public boolean isTransferPendingActiveOrPostTransactionAudit() {		
		return this == TransferPendingActive || this == PostTransactionAudit;
	}

	public String shortDescription() {
		return shortDescription;
	}
}
