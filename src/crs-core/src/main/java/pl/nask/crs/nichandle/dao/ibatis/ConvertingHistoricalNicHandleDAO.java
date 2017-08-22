package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleKey;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalHistoricalNicHandle;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingHistoricalNicHandleDAO extends ConvertingGenericDAO<InternalHistoricalNicHandle, HistoricalObject<NicHandle>, HistoricalNicHandleKey> implements HistoricalNicHandleDAO {

    public ConvertingHistoricalNicHandleDAO(GenericDAO<InternalHistoricalNicHandle, HistoricalNicHandleKey> internalDao, Converter<InternalHistoricalNicHandle, HistoricalObject<NicHandle>> internalConverter) {
        super(internalDao, internalConverter);
    }

    @Override
    public void create(NicHandle nicHandle, Date changeDate, String changedBy) {
        InternalHistoricalNicHandle nh = new InternalHistoricalNicHandle();
        nh.setNicHandleId(nicHandle.getNicHandleId());
        nh.setHistChangeDate(changeDate);
        nh.setChangedByNicHandle(changedBy);
        if (nicHandle.getVat() != null) {
            nh.setVatNo(nicHandle.getVat().getVatNo());
        }
        getInternalDao().create(nh);
    }

}
