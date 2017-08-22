package pl.nask.crs.commons.dao;

import java.util.List;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface Converter<SRC, DST> {

    public DST to(SRC src);

    public List<DST> to(List<SRC> srcs);

    public SRC from(DST dst);

}
