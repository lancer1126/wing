package fun.lance.common.constants.enums;

public enum BusinessType {
    ORDER("ORDER"),
    COMMON("COMMON");

    private final String msg;

    BusinessType(String msg) {
        this.msg = msg;
    }

    public String value() {
        return msg;
    }
}
