package com.trans.actional.controller;

import com.trans.actional.model.User;
import com.trans.actional.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lenovo on 2019/12/3.
 */
@Controller
public class ThymeleafController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute("uid", "<span style='color:red'>12345678</span>");
        model.addAttribute("name", "Jerry");
        return "show";
    }

    @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable int id) {
        User user = userService.sel(id);
        model.addAttribute("user", user);
        return "user";
    }
}
