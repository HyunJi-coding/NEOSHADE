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

        // 세션추가
        HttpSession session = request.getSession();
//            session.setAttribute("ss_member_id", vo_member.getUserId());
            session.setAttribute("ss_login_id", vo_member.getEmail());
            session.setAttribute("ss_name", vo_member.getUsername());
            session.setAttribute("ss_role", vo_member.getRole());
        
        // url 이동 위치
        String strUrl ="/home"; // 성공 이후 가야할 위치

        // Security 가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 있을 경우 URI 등 정보를 가져와서 사용
        if (savedRequest != null) {
            strUrl = savedRequest.getRedirectUrl();
            // 세션에 저장된 객체를 다 사용한 뒤에는 지워줘서 메모리 누수 방지
            requestCache.removeRequest(request, response);
        }

        response.sendRedirect(strUrl);
    }
}
