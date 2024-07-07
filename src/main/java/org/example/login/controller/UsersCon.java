package org.example.login.controller;

import org.example.login.dto.Request.UsersRequest;
import org.example.login.dto.Response.UsersResponse;
import org.example.login.entity.Users;
import org.example.login.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersCon {
    @Autowired
    private UsersService usersService;

    @GetMapping("/list")
    public String userlist(Model model) {
        List<UsersResponse> usersList = usersService.selectAll()
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());

        model.addAttribute("usersList", usersList);
        return "/member/memberlist";
    }

    @GetMapping("/insert")
    public String insert() {
        return "/login/signup";
    }

    @PostMapping("/insert_exe")
    public String insertExe(@ModelAttribute UsersRequest userRequest) {
        Users user = convertToUser(userRequest);
        usersService.insert(user);
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String doSecureLogin(@RequestParam(value = "message", defaultValue = "default", required = false) String strMsg, Model model) {
        model.addAttribute("message", strMsg);
        return "/login/login";
    }

    private UsersResponse convertToUserResponse(Users user) {
        return UsersResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private Users convertToUser(UsersRequest userRequest) {
        return Users.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }
}

