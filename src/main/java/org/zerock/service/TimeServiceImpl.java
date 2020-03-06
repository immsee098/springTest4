package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.zerock.mapper.TimeMapper;

@Service
@Log4j
@AllArgsConstructor
public class TimeServiceImpl implements TimeService {

    private TimeMapper timeMapper;


    @Override
    public String getTime2() {
        return timeMapper.getTime2();
    }
}
