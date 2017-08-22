package pl.nask.crs.nichandle;

/**
 * @author Marianna Mysiorska
 */
public class Vat {

    private String vatNo;

    public Vat(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public int hashCode() {
        int result;
        result = 31 * (vatNo != null ? vatNo.hashCode() : 0);
        return result;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vat))
            return false;

        Vat vat = (Vat) o;
        
        if (vatNo == null && vat.vatNo == null)
            return true;

        if (vatNo != null)
            return vatNo.equals(vat.vatNo);
        else
            return false;                    
    }

}
