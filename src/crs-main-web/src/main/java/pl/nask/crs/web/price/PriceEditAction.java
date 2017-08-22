package pl.nask.crs.web.price;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.price.DomainPrice;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceEditAction extends PriceAction {

    private String id;
    public PriceEditAction(PaymentAppService paymentAppService) {
        super(paymentAppService);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String input() throws Exception {
        DomainPrice domainPrice = paymentAppService.getPrice(getUser(), id);
        wrapper = new PriceWrapper(domainPrice);
        return INPUT;
    }

    public String save() throws Exception {
        paymentAppService.modifyPrice(getUser(), wrapper.getId(), wrapper.getDescription(), wrapper.getPrice(), wrapper.getDuration(), wrapper.getValidFrom(), wrapper.getValidTo(), wrapper.isForRegistration(), wrapper.isForRenewal(), wrapper.isDirect());
        return SUCCESS;
    }
}
