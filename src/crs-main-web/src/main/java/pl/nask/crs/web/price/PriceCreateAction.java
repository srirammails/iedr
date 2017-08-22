package pl.nask.crs.web.price;

import pl.nask.crs.app.payment.PaymentAppService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceCreateAction extends PriceAction {

    public PriceCreateAction(PaymentAppService paymentAppService) {
        super(paymentAppService);
    }

    public String create() throws Exception {
    	paymentAppService.addPrice(getUser(), wrapper.getId(), wrapper.getDescription(), wrapper.getPrice(), wrapper.getDuration(), wrapper.getValidFrom(), wrapper.getValidTo(), wrapper.isForRegistration(), wrapper.isForRenewal(), wrapper.isDirect());
    	return SUCCESS;
    }
}
