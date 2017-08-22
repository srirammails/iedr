package pl.nask.crs.nichandle.service.impl;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.nichandle.service.NicHandleIdGenerator;

/**
 * @author Marianna Mysiorska
 */
class NicHandleIdGeneratorImpl implements NicHandleIdGenerator {

    private NicHandleIdDAO nicHandleIdDAO;

    NicHandleIdGeneratorImpl(NicHandleIdDAO nicHandleIdDAO) {
        Validator.assertNotNull(nicHandleIdDAO, "nic handle id DAO");
        this.nicHandleIdDAO = nicHandleIdDAO;
    }

    public String generateNicHandleId(){
        Long nhSeq = getDBSequence();
        String nicHandleId = "";
        nicHandleId += (char) ((nhSeq / 26 / 26 / 1000) % 26 + (int) 'A');
        nicHandleId += (char) ((nhSeq / 26 / 1000) % 26 + (int) 'A');
        nicHandleId += (char) ((nhSeq / 1000) % 26 + (int) 'A');
// left padding with zeroes
		  nicHandleId += String.format("%3s", (String.valueOf(nhSeq % 1000))).replace(' ', '0').substring(0, 3);
		  nicHandleId += "-IEDR";
        return nicHandleId;
    }

    private Long getDBSequence(){
        nicHandleIdDAO.lockTable();
        Long nicHandleSeq = nicHandleIdDAO.get();
        nicHandleSeq += 1;
        nicHandleIdDAO.update(nicHandleSeq);
        nicHandleIdDAO.unlockTable();
        return nicHandleSeq;
    }
}
