package pl.nask.crs.web.price;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceAction extends AuthenticatedUserAwareAction {

    protected PaymentAppService paymentAppService;

    protected PriceWrapper wrapper;

    public PriceAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public PriceWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(PriceWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String input() throws Exception {
        wrapper = new PriceWrapper();
        return super.input();
    }
}
