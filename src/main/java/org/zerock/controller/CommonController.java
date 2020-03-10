package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j
public class CommonController {

    @GetMapping("/accessError")
    public void accessDenied(Authentication auth, Model model) {
        System.out.println("access Denied : " + auth);
        model.addAttribute("msg", "AccessDenied");
    }

    @GetMapping("/customLogin")
    public void loginInput(String error, String logout, Model model) {
         System.out.println("error:" + error);
         System.out.println("logout :" +logout);

         if(error != null) {
             model.addAttribute("error", "Login Error Check Your Account");
         }

        if (logout != null) {

            model.addAttribute("logout", "Logoit!!");
        }
    }

    @GetMapping("/customLogout")
    public void logoutGET() {
        System.out.println("custom logout");
    }

    @PostMapping("/customLogout")
    public void logoutPost() {
        System.out.println("post custom logout");
    }
}
