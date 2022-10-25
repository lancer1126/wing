package fun.lance.api.user.bo;

import lombok.Data;

@Data
public class UserInfoTokenBO {
    /**
     * 用户在自己系统的用户id
     */
    private Long userId;

    /**
     * 全局唯一的id,
     */
    private Long uid;

    /**
     * 租户id (商家id)
     */
    private Long tenantId;

    /**
     * 系统类型
     */
    private Integer sysType;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String bizUserId;

    private String bizUid;
}
