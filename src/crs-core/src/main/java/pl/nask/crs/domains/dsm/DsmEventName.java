package pl.nask.crs.domains.dsm;

/**
 * Event names handled by the DomainStateMachine
 * 
 * @author Artur Gniadzik
 *
 */
public enum DsmEventName {
	CreateBillableDomainRegistrar,
	CreateBillableDomainDirect,
	CreateCharityDomainRegistrar,
	CreateCharityDomainDirect,
	CreateGIBODomain,
	DeletedDomainRemoval,
    DeletionDatePasses,
    EnterVoluntaryNRP,
    EnterWIPOArbitration,
    ExitWIPOArbitration,
    GIBOAdminFailure,
    GIBOAuthorisation,
    GIBOPaymentFailure,
    GIBOPaymentRetryTimeout,
    PaymentInitiated,
    PaymentSettled,
    RemoveFromVoluntaryNRP,
    RenewalDatePasses,
    SetAutoRenew,
    SetBillable,
    SetCharity,
    SetIEDRPublished,
    SetIEDRUnpublished,
    SetNoAutoRenew,
    SetNonBillable,
    SetOnceAutoRenew,
    SettlementFailure,
    SuspensionDatePasses,
    TransferCancellation,
    TransferRequest,
    TransferToDirect,
    TransferToRegistrar;

    public static DsmEventName forName(String name) {
        for (DsmEventName eventName : DsmEventName.values()) {
            if (eventName.name().equalsIgnoreCase(name)) {
                return eventName;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
