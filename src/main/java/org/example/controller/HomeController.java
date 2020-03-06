package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class HomeController {

        @GetMapping("/")
        public String home(Model model){
            log.info("home!");
            return "home/index";
        }
}
