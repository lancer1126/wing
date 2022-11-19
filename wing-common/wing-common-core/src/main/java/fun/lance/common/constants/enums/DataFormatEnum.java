package fun.lance.common.constants.enums;

public enum DataFormatEnum {
    JSON("json"),
    XML("xml");

    private final String msg;

    DataFormatEnum(String msg) {
        this.msg = msg;
    }

    public String value() {
        return msg;
    }
}
