package pl.nask.crs.ticket.response;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketResponse {

    private long id;

    private String title;

    private String text;

    public TicketResponse() {
    }

    public TicketResponse(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public TicketResponse(long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
