package pl.nask.crs.commons.email.service;

public class EmailDisabledEmailParameters extends MapBasedEmailParameters {

    final private String loggedUser;
    final private String accountUser;

    public EmailDisabledEmailParameters(String loggedUser, String accountUserNH, String accountUserEmail, String accountUserName, String emailsList)
    {
        this.loggedUser = loggedUser;
        this.accountUser = accountUserNH;

        set(ParameterNameEnum.EMAILS_LIST, emailsList);
        set(ParameterNameEnum.BILL_C_NAME, accountUserName);
        set(ParameterNameEnum.BILL_C_EMAIL, accountUserEmail);
    }

    @Override
    public String getLoggedInNicHandle() {
        return loggedUser;
    }

    @Override
    public String getAccountRelatedNicHandle() {
        return accountUser;
    }
}
