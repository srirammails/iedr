package pl.nask.crs.nichandle.dao.ibatis.converters;

import pl.nask.crs.nichandle.dao.ibatis.objects.InternalHistoricalNicHandle;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalNicHandleConverter extends AbstractConverter<InternalHistoricalNicHandle, HistoricalObject<NicHandle>> {

    private NicHandleConverter nicHandleConverter;

    public HistoricalNicHandleConverter(NicHandleConverter nicHandleConverter) {
        Validator.assertNotNull(nicHandleConverter, "nicHandle converter");
        this.nicHandleConverter = nicHandleConverter;
    }

    protected HistoricalObject<NicHandle> _to(InternalHistoricalNicHandle src) {
        return new HistoricalObject<NicHandle>(
                src.getChangeId(),
                nicHandleConverter.to(src),
                src.getHistChangeDate(),
                src.getChangedByNicHandle()
        );
    }

    protected InternalHistoricalNicHandle _from(HistoricalObject<NicHandle> historicalNicHandle) {
        throw new UnsupportedOperationException();
    }

}
