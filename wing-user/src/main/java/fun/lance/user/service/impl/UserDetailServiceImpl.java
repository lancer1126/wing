package fun.lance.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import fun.lance.common.bean.user.dao.AuthAccount;
import fun.lance.common.exception.WingException;
import fun.lance.common.security.model.AuthAccountVerifyBO;
import fun.lance.user.mapper.AuthAccountMapper;
import fun.lance.user.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AuthAccountMapper authAccountMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AuthAccount> accounts = authAccountMapper.selectList(new LambdaQueryWrapper<AuthAccount>()
                        .eq(AuthAccount::getUsername, username));
        if (CollUtil.isEmpty(accounts)) {
            throw new WingException(MessageUtil.get("login.username.notExist"));
        }

        AuthAccountVerifyBO verifyBO = new AuthAccountVerifyBO();
        verifyBO.setAuthAccount(accounts.get(0));
        return verifyBO;
    }
}
