package pl.nask.crs.web.vat;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatCreateAction extends AuthenticatedUserAwareAction {

    private PaymentAppService paymentAppService;

    private VatWrapper wrapper;

    public VatCreateAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public List<String> getCategories() {
        return paymentAppService.getVatCategories(getUser());
    }

    public VatWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(VatWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public String create() {
        try {
            paymentAppService.addVatRate(getUser(), wrapper.getCategory(), wrapper.getFromDate(), wrapper.getVatRate());
            return SUCCESS;
        } catch (VatFromDuplicationException e) {
            addActionError("Cannot create vat rate : for date duplication!");
            return ERROR;
        } catch (ThirdDecimalPlaceException e) {
            addActionError("Cannot create vat rate : third decimal place occured!");
            return ERROR;
        } catch (Exception e) {
            addActionError("ERROR: " + e.getMessage());
            return ERROR;
        }
    }

    @Override
    public String input() throws Exception {
        wrapper = new VatWrapper();
        return super.input();
    }
}
