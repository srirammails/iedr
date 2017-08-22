package pl.nask.crs.nichandle.dao;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import javax.annotation.Resource;

import pl.nask.crs.nichandle.AbstractContextAwareTest;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIdDAOTest extends AbstractContextAwareTest {

    @Resource
    NicHandleIdDAO nicHandleIdDAO;

    @Test
    public void getNicHandleId(){
        Long nicHandleId = nicHandleIdDAO.get();
        assertEquals(nicHandleId.longValue(), 194694);
    }

    @Test
    public void updateNicHandleId(){
        Long nicHandleId = nicHandleIdDAO.get();
        assertEquals(nicHandleId.longValue(), 194694);
        nicHandleIdDAO.update(194695L);
        nicHandleId = nicHandleIdDAO.get();
        assertEquals(nicHandleId.longValue(), 194695);
        nicHandleIdDAO.update(194694L);
        nicHandleId = nicHandleIdDAO.get();
        assertEquals(nicHandleId.longValue(), 194694);
    }

}
