package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {
    @Resource(name = "userServiceImpl")
    private IUserService userService;

    @PostMapping
    @ApiOperation("新增用户接口")
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        log.info("新增用户：{}", userFormDTO);
        User user = new User();
        BeanUtils.copyProperties(userFormDTO, user);
        userService.save(user);
    }
    @DeleteMapping ("{id}")
    @ApiOperation("删除用户接口")
    public void deleteUser(@PathVariable Long id){
        log.info("删除用户：{}", id);
        userService.removeById(id);
    }

    @GetMapping ("{id}")
    @ApiOperation("根据id查询用户")
    public UserVO queryUserById(@PathVariable Long id){
        log.info("查询用户：{}", id);
        User byId = userService.getById(id);
        return BeanUtil.copyProperties(byId, UserVO.class);
    }

    @GetMapping
    @ApiOperation("根据id批量查询用户")
    public List<UserVO> queryUserBatchById(@RequestParam("ids") List<Long> ids){
        log.info("批量查询用户：{}", ids);
        List<User> byIds = userService.listByIds(ids);
        return BeanUtil.copyToList(byIds, UserVO.class);
    }

    @PutMapping("{id}/deduction/{money}")
    @ApiOperation("根据id扣减余额")
    public void updateAccount(@PathVariable String id, @PathVariable Integer money){
        log.info("扣减{}{}额度", id, money);
        userService.updateAccount(id, money);
    }


    @GetMapping
    @ApiOperation("复杂条件查询用户")
    public List<UserVO> queryUsers(UserQuery userQuery){
        log.info("复杂条件查询用户：{}", userQuery);
        List<User> res = userService.queryUsers(userQuery);
    }
}
