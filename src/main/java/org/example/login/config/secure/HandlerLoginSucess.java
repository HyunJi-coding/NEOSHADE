package org.example.login.config.secure;


import org.example.login.entity.Users;
import org.example.login.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class HandlerLoginSucess implements AuthenticationSuccessHandler {

    @Autowired
    UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("===== onAuthenticationSuccess ==== ");

        Users vo_member = usersService.selectEmail(authentication.getName());

        HttpSession session = request.getSession();
            session.setAttribute("ss_member_id", vo_member.getUserId());
            session.setAttribute("ss_login_id", vo_member.getEmail());
            session.setAttribute("ss_name", vo_member.getUsername());
            session.setAttribute("ss_role", vo_member.getRole());
        
        String strUrl ="/home"; // 성공 이후 가야할 위치

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            strUrl = savedRequest.getRedirectUrl();
            requestCache.removeRequest(request, response);
        }

        response.sendRedirect(strUrl);
    }
}
