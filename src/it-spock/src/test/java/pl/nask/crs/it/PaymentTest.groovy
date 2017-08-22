package pl.nask.crs.it

import spock.lang.Specification
import org.springframework.test.context.ContextConfiguration
import pl.nask.crs.payment.service.PaymentService
import javax.annotation.Resource
/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = [
    "/support-config.xml",
    "/payment-full-config.xml",
    "/commons-base-config.xml",
    "/commons.xml",
    "/nic-handles-config.xml",
    "/users-config.xml",
    "/domains-config.xml",
    "/test-config.xml",
    ])
class PaymentTest extends Specification {

     @Resource
     PaymentService paymentService;

    def "get-reservation-for-domain"() {
        expect:
        reservationId == paymentService.getReadyReservation("APITEST-IEDR", param).getId()

        where:
        reservationId | param
        4             | "createCCDomain.ie"
    }
}
