package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.search.SearchCriteria;

public class EmailGroupSearchCriteria  implements SearchCriteria<EmailGroup> {
    protected Long id;
    protected String name;

    public EmailGroupSearchCriteria() {}

    public EmailGroupSearchCriteria(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
