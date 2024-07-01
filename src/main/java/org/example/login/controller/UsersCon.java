package org.example.login.controller;

import org.example.login.entity.Users;
import org.example.login.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersCon {
    @Autowired
    UsersService usersService;

    @GetMapping("/list")
    public String userlist(Model model){
        List<Users> usersList = usersService.selectAll();

        model.addAttribute("usersList",usersList);
        return "/member/memberlist";
    }

    @GetMapping("/insert")
    public String insert(){
        return"/login/signup";
    }

    @PostMapping("/insert_exe")
    public String insertExe(@ModelAttribute Users users){
        usersService.insert(users);
        return "redirect:/home";
    }
}
