package org.zerock.persistence;

import lombok.Setter;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.service.SampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/spring/rootContext.xml")
public class SampleServiceTests {
    static Logger log = Logger.getLogger(SampleServiceTests.class);

    @Setter(onMethod_ = @Autowired)
    private SampleService service;

    @Test
    public void testClass(){
        log.info(service);
        log.info(service.getClass().getName());
    }

    @Test
    public void testAdd() throws Exception {
        //log.info(service.doAdd("123", "456"));
        System.out.println(service.doAdd("123", "345"));

    }
}
