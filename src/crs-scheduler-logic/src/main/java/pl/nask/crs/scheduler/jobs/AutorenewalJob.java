package pl.nask.crs.scheduler.jobs;


import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.payment.PaymentAppService;

public class AutorenewalJob extends AbstractJob {

    private ServicesRegistry servicesRegistry;
    private PaymentAppService paymentAppService;


    public AutorenewalJob(ServicesRegistry servicesRegistry, PaymentAppService paymentAppService) {
        this.servicesRegistry = servicesRegistry;
        this.paymentAppService = paymentAppService;
    }

    @Override
    public void runJob() {
        paymentAppService.autorenewAll(null);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
