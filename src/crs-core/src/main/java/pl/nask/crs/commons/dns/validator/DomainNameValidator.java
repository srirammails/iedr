package pl.nask.crs.commons.dns.validator;

import static pl.nask.crs.commons.dns.validator.InvalidDomainNameException.Reason.*;

import java.util.Arrays;
import java.util.List;

/**
 * It validates whether a given name is a valid domain name according to RFC
 * 1034 syntax or fully qualified domain name.
 *
 * @author Patrycja Wegrzynowicz
 * @author Piotr Tkaczyk
 */
public class DomainNameValidator {

    //    private static final String DOMAIN_PATTERN = "([A-Za-z0-9\\-]+\\.)*[A-Za-z0-9\\-]+";
    /**
     *  According to RFC top level domains should be validated as standard domain,
     *  but IEDR allows only letters to be used.
     */
    private static final String STANDARD_DOMAIN_PATTERN = "([A-Za-z0-9\\-]+\\.)*[A-Za-z]+";

    private static final String IE_DOMAIN_POSTFIX = ".ie";
    private static final String TWO_LETTER_IE_DOMAIN = "^[a-zA-Z]{2}\\.ie$";
    private static final String ONE_CHAR_IE_DOMAIN = "^[a-zA-Z0-9]\\.ie$";

    private DomainNameValidator() {}

    public static void validateIedrName(String domainName) {
        validateName(domainName);
        if (!domainName.endsWith(IE_DOMAIN_POSTFIX))
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
        if (domainName.matches(ONE_CHAR_IE_DOMAIN))
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
        if (domainName.matches(TWO_LETTER_IE_DOMAIN))
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
        if (domainName.charAt(2) == '-' && domainName.charAt(3) == '-')
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);

        List<String> pieces = Arrays.asList(domainName.split("\\."));
        if (pieces.size() > 2 )
            throw new InvalidDomainNameException(domainName, TOO_MANY_LABELS);

    }

    public static String validateName(String name)
            throws InvalidDomainNameException {
        name = normalizeName(name);

        if (name == null)
            throw new InvalidDomainNameException(null, NULL_NAME);

        // root
        if (name.length() == 0)
            return name;

        if (!name.matches(STANDARD_DOMAIN_PATTERN))
            throw new InvalidDomainNameException(name, PATTERN_MISMATCH);

        if (name.length() > 254)
            throw new InvalidDomainNameException(name, NAME_TOO_LONG);

        List<String> pieces = Arrays.asList(name.split("\\."));

        if (pieces.size() < 2)
            throw new InvalidDomainNameException(name, NOT_ENOUGHT_LABELS);

        for (String piece : pieces) {
            if (piece.length() > 63)
                throw new InvalidDomainNameException(name, LABEL_TOO_LONG);
            if (piece.length() == 0)
                throw new InvalidDomainNameException(name, LABEL_EMPTY);
/*
        there exists real domain names that do not conform to this rule e.g. 34sp.com
            if (!Character.isLetter(piece.charAt(0)))
                throw new InvalidDomainNameException(name,
                        LABEL_FIRT_CHAR_NOT_LETTER);
*/
            if (!Character.isLetterOrDigit(piece.charAt(piece.length() - 1)))
                throw new InvalidDomainNameException(name,
                        LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT);

            if (!Character.isLetterOrDigit(piece.charAt(0)))
                throw new InvalidDomainNameException(name,
                        LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT);

        }

        return name;
    }

    public static String normalizeName(String name)
            throws InvalidDomainNameException {
        if (name == null)
            return null;
        name = name.toLowerCase();
        if (name.endsWith("."))
            name = name.substring(0, name.length() - 1);
        return name;
    }

    public static void main(String[] args) {
        validateIedrName("qq.ie.ie");
    }
}
