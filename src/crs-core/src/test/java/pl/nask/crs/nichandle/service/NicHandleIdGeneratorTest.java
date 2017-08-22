package pl.nask.crs.nichandle.service;

import org.testng.annotations.Test;

import javax.annotation.Resource;

import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIdGeneratorTest extends AbstractContextAwareTest {

    @Resource
    NicHandleIdGenerator nicHandleIdGenerator;
    @Resource
    NicHandleIdDAO nicHandleIdDAO;

    @Test
    public void generateNicHandleId(){
        String nicHandleId = nicHandleIdGenerator.generateNicHandleId();
//        assertEquals(nicHandleId, "AHM695-IEDR");
        nicHandleIdDAO.update(194694L);
    }

}
