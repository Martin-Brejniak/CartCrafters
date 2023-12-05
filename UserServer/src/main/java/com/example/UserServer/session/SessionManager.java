package com.example.UserServer.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

    public static void setSessionAttributes(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
        }
    }

    public static void setDefaultSuccessUrl(HttpServletRequest request, String defaultSuccessUrl) {
        HttpSession session = request.getSession();
        session.setAttribute("defaultSuccessUrl", defaultSuccessUrl);
    }

    public static String getDefaultSuccessUrl(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("defaultSuccessUrl");
    }
}
