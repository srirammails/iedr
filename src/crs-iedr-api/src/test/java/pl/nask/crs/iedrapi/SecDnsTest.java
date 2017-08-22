package pl.nask.crs.iedrapi;

import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.CreateType;
import ie.domainregistry.ieapi_domain_1.InfDataType;
import ietf.params.xml.ns.secdns_1.DsOrKeyType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.iedrapi.exceptions.CommandSyntaxErrorException;


/**
 * Check, if secDns is properly serialized/deserialized. 
 * 
 * @author Artur Gniadzik
 *
 */
public class SecDnsTest {
    MyUnmarshaller unmarshaller;

    @BeforeMethod
    public void initUnmarshaller() throws JAXBException {
        unmarshaller = new MyUnmarshaller();
        List<String> schemaFileList = new ArrayList<String>();
        unmarshaller.setSchemaFilesList(schemaFileList );
        unmarshaller.setValidating(false);
    }

    @Test
    public void unmarshallDomainCreate() throws IOException, CommandSyntaxErrorException {
        String domainCreateCmd = IOUtils.toString(getClass().getResourceAsStream("/domain_create.xml"));
        CommandType cmd = unmarshaller.unmarshal(domainCreateCmd, null);
        CreateType domainCreate = ((JAXBElement<CreateType>) cmd.getCreate().getAny()).getValue();
        AssertJUnit.assertNotNull(domainCreate.getCreate());
        Assert.assertNotNull(domainCreate.getCreate().getMaxSigLife());
    }

    @Test
    public void marshallDomainInfo() throws JAXBException {
        InfDataType inf = new InfDataType();
        DsOrKeyType value = new DsOrKeyType();
        value.setMaxSigLife(10);
        inf.setInfData(value );
        ResponseType res = ResponseTypeFactory.success(inf);

        MyMarshaller m = new MyMarshaller(res);
        String s = m.marshall();
        AssertJUnit.assertTrue("msg should contain 'secDNS:infData', but it doesn't: " + s, s.contains("secDNS:infData"));
    }
}
