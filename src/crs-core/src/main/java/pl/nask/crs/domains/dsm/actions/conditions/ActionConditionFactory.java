package pl.nask.crs.domains.dsm.actions.conditions;

public enum ActionConditionFactory {
	DIRECT_TYPE {
		@Override
		public ActionCondition conditionFor(String condition) {
			return DirectTypeCondition.valueOf(condition);
		}
	}, PAY_METHOD {
		@Override
		public ActionCondition conditionFor(String condition) {
			return PayMethodCondition.valueOf(condition);
		}
	}, RENEWAL_DIFF {
		@Override
		public ActionCondition conditionFor(String condition) {
			RenewalDiffCondition renewalDiffCondition = new RenewalDiffCondition(condition);
			return renewalDiffCondition;
		}
	};
	
	public abstract ActionCondition conditionFor(String condition);
}