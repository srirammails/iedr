package pl.nask.crs.ticket.dao.ibatis.objects;

public class InternalTicketNameserver {

    private String name;
    private Integer nameFailureReason;
    private String ip;
    private Integer ipFailureReason;

    public InternalTicketNameserver() {
    }

    public InternalTicketNameserver(String name, Integer nameFailureReason, String ip, Integer ipFailureReason) {
        this.name = name;
        this.nameFailureReason = nameFailureReason;
        this.ip = ip;
        this.ipFailureReason = ipFailureReason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNameFailureReason() {
        return nameFailureReason;
    }

    public void setNameFailureReason(Integer nameFailureReason) {
        this.nameFailureReason = nameFailureReason;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIpFailureReason() {
        return ipFailureReason;
    }

    public void setIpFailureReason(Integer ipFailureReason) {
        this.ipFailureReason = ipFailureReason;
    }

}
