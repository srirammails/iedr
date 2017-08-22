package pl.nask.crs.app.invoicing;

import pl.nask.crs.payment.service.PaymentSender;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PaymentSenderTestImpl  implements PaymentSender {

    private final static String properResponse ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<response timestamp=\"20120319115651\">\n" +
            "<merchantid>iedr</merchantid>\n" +
            "<account>internet</account>\n" +
            "<orderid>20120319125630-103067</orderid>\n" +
            "<authcode>115651</authcode>\n" +
            "<result>00</result>\n" +
            "<cvnresult>U</cvnresult>\n" +
            "<avspostcoderesponse>U</avspostcoderesponse>\n" +
            "<avsaddressresponse>U</avsaddressresponse>\n" +
            "<batchid>-1</batchid>\n" +
            "<message>[ test system ] AUTH CODE 115651</message>\n" +
            "<pasref>133215821111512</pasref>\n" +
            "<timetaken>0</timetaken>\n" +
            "<authtimetaken>0</authtimetaken>\n" +
            "\n" +
            "<cardissuer>\n" +
            "<bank>AIB BANK</bank>\n" +
            "<country>IRELAND</country>\n" +
            "<countrycode>IE</countrycode>\n" +
            "<region>EUR</region>\n" +
            "</cardissuer>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<md5hash>84cb753995cded64ff90a9a20bf2866f</md5hash>\n" +
            "</response>";

    public String send(String commandXML) {
        return properResponse;
    }

}
