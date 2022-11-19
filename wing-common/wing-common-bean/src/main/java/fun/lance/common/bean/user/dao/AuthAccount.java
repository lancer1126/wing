package fun.lance.common.bean.user.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import fun.lance.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthAccount extends BaseModel {

    @TableId
    private Long uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建ip
     */
    private String createIp;

    /**
     * 状态 1:启用 0:禁用 -1:删除
     */
    private Integer status;

    /**
     * 系统类型见SysTypeEnum 0.普通用户系统 1.商家端
     */
    private Integer sysType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 所属租户
     */
    private Long tenantId;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;
}
