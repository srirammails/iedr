package pl.nask.crs.domains;

public class DsmState {

	private static final DsmState INITIAL_DSM_STATE = new DsmStateStub(0);
	
	private int id;

	private Boolean wipo;

	private CustomerType customerType;

	private NRPStatus nrpStatus;

	private RenewalMode renewalMode;

	private DomainHolderType holderType;
	
	private Boolean published;

	public static DsmState initialState() {		
		return INITIAL_DSM_STATE;
	}
	
	protected DsmState(int id) {
		this.id = id;
	}
	
	protected DsmState() {
	}
	
	public int getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	public Boolean getWipoDispute() {
		return wipo;
	}
	
	protected void setWipoDispute(Boolean wipo) {
		this.wipo = wipo;
	}
	
	public CustomerType getCustomerType() {
		return customerType != null ? customerType : CustomerType.NA;
	}
	
	protected void setCustomerType(CustomerType type) {
		this.customerType = type;
	}
	
	public NRPStatus getNRPStatus() {
		return nrpStatus != null ? nrpStatus : NRPStatus.NA;
	}
	
	protected void setNRPStatus(NRPStatus status) {
		this.nrpStatus = status;
	}
	
	public RenewalMode getRenewalMode() {
		return renewalMode != null ? renewalMode : RenewalMode.NA;
	}
	
	protected void setRenewalMode(RenewalMode mode) {
		this.renewalMode = mode;
	}
	
	public DomainHolderType getDomainHolderType() {
		return holderType != null ? holderType : DomainHolderType.NA;
	}
	
	protected void setDomainHolderType(DomainHolderType type) {
		this.holderType = type;
	}
	
	public Boolean getPublished() {
		return published;
	}
	
	public void setPublished(Boolean published) {
		this.published = published;
	}
	
	public boolean isValid() {
		return true;
	}

	@Override
	public String toString() {
		return "DsmState [id=" + id + ", nrpStatus=" + nrpStatus + ", wipo="
				+ wipo + ", customerType=" + customerType + ", renewalMode="
				+ renewalMode + ", holderType=" + holderType + ", published=" + published +"]";
	}
	
	
}
