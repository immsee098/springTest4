package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import java.util.List;
import java.util.stream.IntStream;

@Log4j
@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/resources/spring/rootContext.xml")
public class ReplyMapperTests {

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Test
    public void testMapper(){
        log.info(mapper);
    }

    private Long[] bnoArr = {31L, 33L, 37L, 38L, 39L};

    @Test
    public void testCreate(){

        IntStream.rangeClosed(1, 10).forEach(i->{
            ReplyVO vo = new ReplyVO();

            //게시물의 번호
            vo.setBno(bnoArr[i%5]);
            vo.setReply("댓글 테스트" + i);
            vo.setReplyer("replyer" + i);

            mapper.insert(vo);
        });
    }

    @Test
    public void testRead(){
        Long targetRno = 5L;
        ReplyVO vo = mapper.read(targetRno);
        log.info(vo);
    }

    @Test
    public void testDelete(){
        Long targetRno = 2L;

        mapper.delete(Math.toIntExact(targetRno));
    }

    @Test
    public void testUpdate(){
        Long targetRno = 10L;
        ReplyVO vo = mapper.read(targetRno);
        vo.setReply("Update Reply");
        int count = mapper.update(vo);
        log.info("UPDATE COUNT : "+count);
    }

    @Test
    public void testList() {
        Criteria cri = new Criteria();
        List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
        replies.forEach(reply->log.info(reply));
    }

    @Test
    public void testList2(){
        Criteria cri = new Criteria(1, 10);
        List<ReplyVO> replies = mapper.getListWithPaging(cri, 39L);
        replies.forEach(reply -> log.info(reply));
    }
}
