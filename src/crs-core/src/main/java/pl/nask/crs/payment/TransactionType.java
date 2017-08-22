package pl.nask.crs.payment;

/**
 * @author: Marcin Tkaczyk
 */
public enum TransactionType {
    TYPE_AUTH(1), TYPE_SETTLE(2), TYPE_VOID(3);
    private int type;

    TransactionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
