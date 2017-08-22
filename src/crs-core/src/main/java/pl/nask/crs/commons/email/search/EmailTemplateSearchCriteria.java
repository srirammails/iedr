package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.search.SearchCriteria;

public class EmailTemplateSearchCriteria implements SearchCriteria<EmailTemplate> {
    protected Integer id;
    protected String name;
    protected Long groupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
