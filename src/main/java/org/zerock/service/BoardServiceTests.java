package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/spring/rootContext.xml")
@Log4j
public class BoardServiceTests {

    @Setter(onMethod_ = {@Autowired})
    private BoardService service;


    //BoardServiceImpl로부터 주입 당하는지 확인하는 부분
    @Test
    public void testExist(){

        log.info(service);
    }

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");

        service.register(board);

        log.info("생성된 게시물의 번호 : "+board.getBno());
    }

    @Test
    public void testGet(){
        log.info(service.get(1L));
    }

    @Test
    public void testDelete() {
        log.info("REMOVE RESULT : " + service.remove(2L));
    }

    @Test
    public void testUpdate() {
        BoardVO board = service.get(1L);

        if(board == null) {
            return;
        }

        board.setTitle("제목 수정합니다.");
        log.info("MODIFY RESULT : " + service.modify(board));

    }

    @Test
    public void testGetList(){
        service.getList(new Criteria(2,10)).forEach(board->log.info(board));
    }
}
