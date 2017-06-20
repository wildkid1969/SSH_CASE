package com.rollingStones.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rollingStones.entity.Page;
import com.rollingStones.entity.User;
import com.rollingStones.service.UserService;


/**
 * Created by mark on 4/24/15.
 */

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;
    
    public static final int  PAGE_SIZE = 10;

    @RequestMapping("index")
    @ResponseBody
    public Page<User> index(Integer page,User user){
    	return userService.getUserByParam(page, PAGE_SIZE, user);
    }
    
    @RequestMapping("getAllUsers")
    @ResponseBody
    public List<User> getAllUsers(){
      return userService.getAllUsernames();
    }
    
    
    @RequestMapping("save")
    public ModelAndView home(String userName){
        List<User> us = new ArrayList<User>();
        User u = new User();
        u.setUserName(userName);
        us.add(u);
        userService.saveUsers(us);
        return new ModelAndView("index","userName",userName);
    }


}
