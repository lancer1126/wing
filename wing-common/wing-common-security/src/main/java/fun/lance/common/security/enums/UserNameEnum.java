package fun.lance.common.security.enums;

public enum UserNameEnum {

    USERNAME(1),

    /**
     * 手机号
     */
    PHONE(2),

    /**
     * 邮箱
     */
    EMAIL(3),;

    private final Integer value;

    public Integer value() {
        return value;
    }

    UserNameEnum(Integer value) {
        this.value = value;
    }
}
