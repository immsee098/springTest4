package org.zerock.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.service.TimeService;

@Controller
@RequestMapping("/time/*")
@Log4j
public class TimeController {

    @Setter
    private TimeService timeService;

    @GetMapping("/getTime2")
    public void getTime2(){
        String time = timeService.getTime2();
        log.info("getTime2");
        log.info(time);
    }
}
