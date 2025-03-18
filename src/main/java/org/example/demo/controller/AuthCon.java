package org.example.login.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
public class AuthCon {
    @GetMapping("/check-auth")
    public boolean checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @GetMapping("/sessionInfo")
    public String doSession(HttpServletRequest req){
        String strReturn="";
        HttpSession session = req.getSession();

        String strSessionId = session.getId();
        strReturn= "session ID : "+strSessionId+"<br>";

        Enumeration<String> names = session.getAttributeNames();

        String strSession="";
        while(names.hasMoreElements()){
            strSession = names.nextElement();
            strReturn += strSession+" : "+ session.getAttribute(strSession) +"</br>";
        }

        return strReturn;
    }
}