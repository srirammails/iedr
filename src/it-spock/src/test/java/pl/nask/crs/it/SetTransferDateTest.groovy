package pl.nask.crs.it

import spock.lang.Specification
import pl.nask.crs.domains.dao.DomainDAO
import pl.nask.crs.domains.dsm.actions.SetTransferDate
import pl.nask.crs.domains.Domain
import pl.nask.crs.contacts.Contact
import pl.nask.crs.accounts.Account
import pl.nask.crs.domains.dsm.events.TransferToRegistrar
/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
class SetTransferDateTest extends Specification {

    def "should-write-hist-record"() {
        setup:
        def dao = Mock(DomainDAO);
        def action = (SetTransferDate)new SetTransferDate(dao).getAction(null);
        def domain = new Domain("name.ie",
                "holder",
                "holderClass",
                "holderCategory",
                new Contact("any"),
                new Account(1),
                new Date(),
                new Date(),
                "remark",
                new Date(),
                false,
                [new Contact("any")],
                [new Contact("any")],
                [new Contact("any")],
                []
        );
        def event = new TransferToRegistrar(new Contact("old"), new Contact("new"))

        when: "dsm action is invoked"
        action.invoke(domain,event)

        then: "historical record is created"
        1 * dao.createTransferRecord("name.ie", action.getNewTransferDate(), "old", "new")
    }
}
