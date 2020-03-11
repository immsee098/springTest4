package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.MemberVO;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/resources/spring/rootContext.xml"})
@Log4j
public class MemberMapperTests {
    @Setter(onMethod_ = @Autowired)
    private MemberMapper mapper;

    @Test
    public void testRead(){
        MemberVO vo = mapper.read("admin90");
        System.out.println(vo);
        vo.getAuthList().forEach(authVO -> System.out.println(authVO));
    }
}