package pl.nask.crs.api.vo;

import pl.nask.crs.payment.LimitsPair;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * @author: Marcin Tkaczyk
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LimitsPairVO {

    private Double min;
    private Double max;

    public LimitsPairVO() {
    }

    public LimitsPairVO(LimitsPair limitsPair) {
        this.min = limitsPair.getMin();
        this.max = limitsPair.getMax();
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
