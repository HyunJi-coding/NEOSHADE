package org.example.login.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@RequestMapping("/comm")
public class CommCon {
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
            //System.out.println(strSession +" : "+ session.getAttribute(strSession));
        }

        return strReturn;
    }

}
