package org.example.login.controller;

import org.example.login.dto.Request.UsersRequest;
import org.example.login.dto.Response.UsersResponse;
import org.example.login.entity.Users;
import org.example.login.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersCon {
    @Autowired
    private UsersService usersService;

    @GetMapping("/insert")
    public String insert() {
        return "/login/signup";
    }

    @PostMapping("/insert_exe")
    public String insertExe(@ModelAttribute UsersRequest userRequest) {
        Users user = UsersRequest.fromEntity(userRequest);
        usersService.insert(user);
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String doSecureLogin(@RequestParam(value = "message", defaultValue = "default", required = false) String strMsg, Model model) {
        model.addAttribute("message", strMsg);
        return "/login/login";
    }

    @GetMapping("/update")
    public String showEditForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");


        Users users = usersService.getUserById(userId);
        model.addAttribute("users", users);

        return "/member/userupdate";
    }

    @PostMapping("/update_exe")
    public String updateUser(HttpServletRequest request, @RequestParam String password, @RequestParam String gender, @RequestParam String birthDay) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");
        usersService.updateUser(userId, password, gender, birthDay);
        return "redirect:/users/" + userId;
    }



}

