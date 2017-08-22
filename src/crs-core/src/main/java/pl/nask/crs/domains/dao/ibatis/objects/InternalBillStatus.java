package pl.nask.crs.domains.dao.ibatis.objects;

import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.statuses.dao.ibatis.objects.InternalStatus;

/**
 * @author Kasia Fulara
 */
public class InternalBillStatus extends InternalStatus implements BillingStatus {

    private String detailedDescription;
    private String Colour;

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

	@Override
	public boolean isMSD() {
		return 
			"M".equals(getDescription()) ||
			"S".equals(getDescription()) ||
			"D".equals(getDescription());
	}
}
