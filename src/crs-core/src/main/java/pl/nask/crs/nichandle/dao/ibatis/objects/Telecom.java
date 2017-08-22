package pl.nask.crs.nichandle.dao.ibatis.objects;

import org.apache.log4j.Logger;

public class Telecom {

    public static enum Type {
        PHONE("P"),
        FAX("F");

        private final String name;
        private Type(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type getByName(final String name) {
            if (name.equals("P"))
                return PHONE;
            if (name.equals("F"))
                return FAX;
            Logger.getLogger(Telecom.class).error("Unknown telecom type " + name + ". Assuming phone.");
            return PHONE;
        }
    }

    private String number;
    private Type type;

    public Telecom() {
        this("", Type.PHONE);
    }
    public Telecom(String number, String typeName) {
        this(number, Type.getByName(typeName));
    }

    public Telecom(String number, Type type) {
        this.number = number;
        this.type = type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public void setTypeName(String typeName) {
        this.setType(Type.getByName(typeName));
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
