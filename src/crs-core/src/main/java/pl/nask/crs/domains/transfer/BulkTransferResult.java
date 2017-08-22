package pl.nask.crs.domains.transfer;

import java.util.List;
import java.util.Map;

public class BulkTransferResult {

	public static BulkTransferResult error(List<String> requestedDomainNames,
			Map<String, String> validationErrors) {
		// FIXME: implement me
		throw new UnsupportedOperationException("unimplemented");
	}

	public static BulkTransferResult success(List<String> requestedDomainNames) {
		// FIXME: implement me
		throw new UnsupportedOperationException("unimplemented");
	}

}
