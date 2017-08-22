package pl.nask.crs.invoicing.service.impl;

import ie.domainregistry.invoice_1.AccountType;
import ie.domainregistry.invoice_1.CurrencyType;
import ie.domainregistry.invoice_1.DomainType;
import ie.domainregistry.invoice_1.HolderType;
import ie.domainregistry.invoice_1.IedrType;
import ie.domainregistry.invoice_1.InvoiceType;
import ie.domainregistry.invoice_1.ItemType;
import ie.domainregistry.invoice_1.ObjectFactory;
import ie.domainregistry.invoice_1.OperationType;
import ie.domainregistry.invoice_1.PaymentType;
import ie.domainregistry.invoice_1.PeriodType;
import ie.domainregistry.invoice_1.ProductType;
import ie.domainregistry.invoice_1.TransactionList;
import ie.domainregistry.invoice_1.TransactionType;
import ie.domainregistry.invoice_1.VatRateItem;
import ie.domainregistry.invoice_1.VatRateTotalItem;
import ie.domainregistry.invoice_1.VatRateTotalType;
import ie.domainregistry.invoice_1.VatRatesType;
import ie.domainregistry.invoice_1.VatTotalType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountHelperService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.invoicing.service.InvoiceConversionException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.VatDictionary;

public class InvoiceToXmlConverter {
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	private static final ObjectFactory factory = new ObjectFactory();
	private final VatDictionary vatDictionary;	
	private final Dictionary<String, DomainPrice> priceDictionary;
	private final DomainSearchService domainService;
	private final AccountHelperService accountHelperService;
    private final ApplicationConfig applicationConfig;
	
	public InvoiceToXmlConverter(VatDictionary vatDictionary, Dictionary<String, DomainPrice> priceDictionary, DomainSearchService domainSearchService, AccountHelperService accountHelperService, ApplicationConfig applicationConfig) {
		this.vatDictionary = vatDictionary;
		this.priceDictionary = priceDictionary;
		this.domainService = domainSearchService;
		this.accountHelperService = accountHelperService;
        this.applicationConfig = applicationConfig;
	}
	
	public JAXBElement<InvoiceType> convertToJAXBInvoice(Invoice invoice) throws InvoiceConversionException {
		InvoiceType invoiceType = convertToInvoiceType(invoice);
		return factory.createInvoice(invoiceType);
	}

	private InvoiceType convertToInvoiceType(Invoice invoice) throws InvoiceConversionException {		
		InvoiceType t = new InvoiceType();
		try {
			t.setAccount(extractAccountType(invoice));
			t.setIedr(extractIedrType());// configuration!
		} catch (AccountNotFoundException e) {
			throw new InvoiceConversionException("Couldn't get account data", e);
		}
		
		t.setPaidBy(invoice.getPaymentMethod().getFullName());
		t.setCurrency(CurrencyType.EUR);
		t.setDate(invoice.getInvoiceDate());
        t.setDateString(FORMATTER.format(invoice.getInvoiceDate()));
		t.setNumber(invoice.getInvoiceNumber());
		t.setPayment(paymentType(invoice));
		t.setTotalExVAT(money(invoice.getTotalNetAmount()));
		t.setTotalIncVAT(money(invoice.getTotalCost()));
		t.setTotalVAT(vatTotalType(invoice));
		t.setTransactions(transactions(invoice));
		t.setVatRates(convertVatDictionary());		
		return t;
	}

    private VatTotalType vatTotalType(Invoice invoice) {
        VatTotalType vatTotalType = new VatTotalType();
        vatTotalType.setTotal(money(invoice.getTotalVatAmount()));
        vatTotalType.setVatRateTotal(vatRateTotalType(invoice));
        return vatTotalType;
    }

    private VatRateTotalType vatRateTotalType(Invoice invoice) {
        VatRateTotalType vrtt = new VatRateTotalType();
        for (Map.Entry<BigDecimal, BigDecimal> entry : invoice.getTotalVatMap().entrySet()) {
            VatRateTotalItem item = new VatRateTotalItem();
            item.setRate(entry.getKey());
            item.setTotal(entry.getValue());
            vrtt.getRate().add(item);
        }
        return vrtt;
    }

    private IedrType extractIedrType() throws AccountNotFoundException {
		IedrType iedrType = new IedrType();
		NicHandle a = accountHelperService.getIEDRAccount();
		iedrType.setAddress1(multilined(a.getAddress()));
//		iedrType.setAddress2(value)
//		iedrType.setAddress3(value)
		iedrType.setCountry(a.getCountry());
		iedrType.setCounty(a.getCounty());
		iedrType.setName(a.getName());
		iedrType.setVatNo(a.getVat().getVatNo());
		return iedrType;
	}

	private String multilined(String address) {
		return MultilinedStringBuilder.buildMultilinedFrom(address, ",");
	}

	private BigDecimal money(int amountInLowestCurrencyUnit) {
		return MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(amountInLowestCurrencyUnit);
	}

    private TransactionList transactions(Invoice invoice) throws InvoiceConversionException {
		TransactionList transactions = new TransactionList();
		
		for (Transaction t: invoice.getTransactions()) {
			TransactionType transactionT = new TransactionType();
			transactionT.setMethod(ie.domainregistry.invoice_1.PaymentMethod.valueOf(t.getPaymentMethod().name()));
			transactionT.setOrderID(t.getOrderId());
			transactionT.setType(OperationType.fromValue(operationType(t)));
			transactionT.setDate(t.getFinanciallyPassedDate());
            transactionT.setDateString(FORMATTER.format(t.getFinanciallyPassedDate()));
			transactionT.setTime(t.getFinanciallyPassedDate());
			
			for (Reservation r: t.getReservations()) {
				DomainPrice dp = priceDictionary.getEntry(r.getProductCode());
				if (dp == null) {
					throw new InvoiceConversionException("Product not found: " + r.getProductCode(), null);
				}
				Domain d;
				try {
					d = domainService.getDomain(r.getDomainName());
				} catch (DomainNotFoundException e) {
					throw new InvoiceConversionException("Domain not found: " + r.getDomainName(), e);
				}
				
				ItemType item = new ItemType();
				item.setCost(r.getNetAmount());
				item.setDomain(domainType(r, d));
				item.setHolder(holderType(d));
				item.setId(r.getId());
				item.setPeriod(period(r.getStartDate(), r.getEndDate()));
				item.setProduct(productType(dp));
				item.setTotal(r.getTotal());
				item.setUnit(getUnitPrice(dp));
				item.setVATAmount(r.getVatAmount());
				item.setVATCode(r.getVatCategory());
				item.setVATRate(BigDecimal.valueOf(r.getVatRate()).setScale(3, RoundingMode.HALF_EVEN).stripTrailingZeros());
				item.setVATRateID(r.getVatId());				
				
				transactionT.getItem().add(item);
			}

			transactions.getTransaction().add(transactionT);
		}
		
		return transactions;
	}

    private BigDecimal getUnitPrice(DomainPrice domainPrice) {
        return MoneyUtils.divide(domainPrice.getPrice(), domainPrice.getDuration());
    }

	private PeriodType period(Date startDate, Date endDate) {
		PeriodType pt = new PeriodType();
		pt.setStartDate(startDate);
		pt.setEndDate(endDate);
		return pt;
	}

	private HolderType holderType(Domain d) {
		HolderType ht = new HolderType();
		ht.setName(d.getHolder());
		return ht;
	}

	private DomainType domainType(Reservation r, Domain d) {
		DomainType dt = new DomainType();
		dt.setName(d.getName());
		dt.setRegistrationDate(d.getRegistrationDate());
		dt.setRenewalDate(r.getEndDate());
		if (r.getOperationType() == pl.nask.crs.payment.OperationType.TRANSFER) {
			dt.setTransferDate(d.getTransferDate());
		}
		return dt;
	}

	private ProductType productType(DomainPrice dp) {
		ProductType p = new ProductType();
		p.setId(dp.getId());
		p.setProduct(dp.getDescription());
		p.setYears(dp.getDuration());
		return p;
	}

	private String operationType(Transaction t) {
		if (t.getReservations() == null || t.getReservations().isEmpty()) {
			throw new IllegalArgumentException("Invoice transaction without reservations! (invoice ID: " + t.getInvoiceId() + ")");
		}
		return t.getReservations().get(0).getOperationType().getTypeName();
	}

	private PaymentType paymentType(Invoice invoice) {
		PaymentType pt = new PaymentType();
		pt.setAmount(MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(invoice.getTotalCost()).negate());
		List<Transaction> trans = invoice.getTransactions();
		if (trans == null || trans.isEmpty()) {
			throw new IllegalStateException ("Invoice with no transactions! (invoiceNo: " + invoice.getInvoiceNumber() + ")");
		}
				
		PaymentMethod method = trans.get(0).getPaymentMethod();		
		pt.setMethod(ie.domainregistry.invoice_1.PaymentMethod.valueOf(method.name()));
		return pt;
	}

	private VatRatesType convertVatDictionary() {
		VatRatesType vrt = new VatRatesType();
		for (Vat v: vatDictionary.getEntries()) {
			VatRateItem item = new VatRateItem();
			item.setCode(v.getCategory());
			item.setId(v.getId());
			BigDecimal rate = new BigDecimal(v.getVatRate() * 10000);
			rate = rate.setScale(2, RoundingMode.HALF_DOWN);
			item.setRate(rate);
			vrt.getRate().add(item );
		}
		return vrt;
	}

	protected AccountType extractAccountType(Invoice invoice) throws AccountNotFoundException {
		AccountType t = new AccountType();
		t.setName(isDirect(invoice.getAccountNumber()) ? invoice.getBillingNicHandleName() : invoice.getAccountName());
		t.setAccountNo(invoice.getAccountNumber());
		t.setBillC(invoice.getBillingNicHandle());
		t.setAddress1(multilined(invoice.getAddress1()));
		t.setAddress2(invoice.getAddress2());
		t.setAddress3(invoice.getAddress3());
		t.setCountry(invoice.getCountry());
		t.setCounty(invoice.getCounty());
		t.setVatNo(vatNumberForAccount(invoice.getAccountNumber()));		
		return t;
	}

    private boolean isDirect(long accountNumber) {
        return accountNumber == applicationConfig.getGuestAccountId();
    }

    private String vatNumberForAccount(long accountNumber) throws AccountNotFoundException {
		return accountHelperService.getVatNo(accountNumber);
	}
	
}
