package pl.nask.crs.country;

import java.util.List;

public interface Country {
    
    public int getCode();

    public String getName();

    public List<String> getCounties();

	public String getVatCategory();
}
