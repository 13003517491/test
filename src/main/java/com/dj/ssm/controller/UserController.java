package com.dj.ssm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.ssm.common.utils.ResultModel;
import com.dj.ssm.common.utils.SystemConstant;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    private ResultModel<Object> login (String userName, String password, User user) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return new ResultModel<Object>().error(SystemConstant.NAME_PWD_EMPTY);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(user);
        User user2 = userService.getOne(queryWrapper);
        if (null == user2) {
            return new ResultModel<Object>().error(SystemConstant.NAME_PWD_ERROR);
        }
        return new ResultModel<>().success();
    }

    @RequestMapping("show")
    public ResultModel<Object> show(User user, Integer minAge, Integer maxAge, Integer pageNo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (!StringUtils.isEmpty(user.getUserName())) {
            queryWrapper.like("user_name", user.getUserName());
        }
        if (!StringUtils.isEmpty(user.getPhone())) {
            queryWrapper.like("phone", user.getPhone());
        }
        if (!StringUtils.isEmpty(user.getEmail())) {
            queryWrapper.like("email", user.getEmail());
        }
        if (null != minAge && null != maxAge) {
            queryWrapper.between("age", minAge, maxAge);
        }
        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(2);
        IPage iPage = userService.pageMaps(page, queryWrapper);
        resultMap.put("totalNum", iPage.getPages());
        resultMap.put("userList", iPage.getRecords());
        return new ResultModel<>().success(resultMap);
    }

    @RequestMapping("delById")
    public ResultModel<Object> delById(Integer id) {
        userService.removeById(id);
        return new ResultModel<>().success();
    }

    @RequestMapping("update")
    public ResultModel<Object> update(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_name", user.getUserName())
                    .set("password", user.getPassword())
                    .set("age", user.getAge())
                    .set("phone", user.getPhone())
                    .set("email", user.getEmail());
        updateWrapper.eq("id", user.getId());
        userService.update(updateWrapper);
        return new ResultModel<>().success();
    }

    @RequestMapping("add")
    public ResultModel<Object> add(User user) {
        userService.save(user);
        return new ResultModel<>().success();
    }
}
