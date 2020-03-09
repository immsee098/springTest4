package org.zerock.domain;

import lombok.Data;

import java.util.List;

@Data
public class BoardVO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private Data regdate;
    private Data updateDate;

    private int replyCnt;
    private List<BoardAttachVO> attachList;

}
