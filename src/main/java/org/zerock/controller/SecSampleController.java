package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SecSampleController {

    @GetMapping("/all")
    public void doAll() {

        System.out.println("do all can access everybody");
    }

    @GetMapping("/member")
    public void doMember() {
        System.out.println("logined member");
    }

    @GetMapping("/admin")
    public void doAdmin(){
        System.out.println("admin only");
    }
}
