package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public void updateAccount(String id, Integer money) {
        // 1 查询用户
        User byId = this.getById(id);
        // 2 校验status
        if (byId == null || byId.getStatus() == 2) {
            throw new RuntimeException("用户状态异常");
        }
        // 3 判断余额是否充足
        if (byId.getBalance() < money){
            throw new RuntimeException("余额不足");
        }
        // 4 扣款
        byId.setBalance(byId.getBalance() - money);
        this.updateById(byId);
    }

    @Override
    public List<User> queryUsers(UserQuery userQuery) {
        return List.of();
    }
    ?????
}
