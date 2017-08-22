package pl.nask.crs.country.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.country.Country;

public class InternalCountry implements Country {

    private String name;
    private List<String> counties = new ArrayList<String>();
    private int code;
    private String vatCategory;

    public int getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getCounties() {
        return counties;
    }

    public String getName() {
        return name;
    }

	@Override
	public String getVatCategory() {
		return vatCategory;
	}

	public void setVatCategory(String vatCategory) {
		this.vatCategory = vatCategory;
	}
}
