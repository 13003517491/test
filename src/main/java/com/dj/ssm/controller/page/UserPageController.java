package com.dj.ssm.controller.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserPageController {
    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin() {
        return "user/login";
    }

    @RequestMapping("toShow")
    public String toShow() {
        return "user/show";
    }

    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, Model model) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userService.getOne(queryWrapper);
        model.addAttribute("user", user);
        return "user/update";
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        return "user/add";
    }
}
