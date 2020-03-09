package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
@Controller
public class BoardController {

    @Setter
    private BoardService service;

//    @GetMapping("/list")
//    public void list(Model model){
//
//        log.info("list");
//        model.addAttribute("list", service.getList());
//    }

    @GetMapping("/list")
    public void list(Criteria cri, Model model){
        log.info("list : "+cri);
        model.addAttribute("list", service.getList(cri));
        //model.addAttribute("pageMaker", new PageDTO(cri, 123));

        int total = service.getTotal(cri);
        log.info("total:" +total);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr){

        System.out.println("=================");
        System.out.println("register: " + board);

        if(board.getAttachList() != null) {
            board.getAttachList().forEach(attach -> System.out.println(attach));
        }
        System.out.println("=================");

        //log.info("register : " + board);
        service.register(board);
        //rttr.addFlashAttribute("result", board.getBno());

        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam(value = "bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model){

        log.info("/get or /modify");

        model.addAttribute("board", service.get(bno));
    }


    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("modify : "+ board);

        if(service.modify(board)){
            rttr.addFlashAttribute("result", "success");
        }

//        rttr.addAttribute("pageNum", cri.getPageNum());
//        rttr.addAttribute("amount", cri.getAmount());
//        rttr.addAttribute("type", cri.getType());
//        rttr.addAttribute("keyword", cri.getKeyword());

//        return "redirect:/board/list";
        return "redirect:/board/list" + cri.getListLink();
    }


    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("remove......"+bno);

        List<BoardAttachVO> attachList = service.getAttachList(bno);

        if(service.remove(bno)){

            deleteFiles(attachList);

            rttr.addFlashAttribute("result", "success");
        }

//        rttr.addAttribute("pageNum", cri.getPageNum());
//        rttr.addAttribute("amount", cri.getAmount());
//        rttr.addAttribute("type", cri.getType());
//        rttr.addAttribute("keyword", cri.getKeyword());

//        return "redirect:/board/list";
        return "redirect:/board/list"+cri.getListLink();
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
        System.out.println("getAttachList : " + bno);
        return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
    }

    private void deleteFiles(List<BoardAttachVO> attachList){
        if(attachList == null || attachList.size() == 0){
            return;
        }

        System.out.println("delete attach files......");
        System.out.println(attachList);

        attachList.forEach(attach -> {
            try{
                Path file = Paths.get("/Users/yhs/Desktop/temp/"+attach.getUploadPath()+"/"+attach.getUuid()+"_"+ attach.getFileName());

                Files.deleteIfExists(file);

                if(URLConnection.guessContentTypeFromName(String.valueOf(file)).startsWith("image")){
                    Path thumbnail = Paths.get("/Users/yhs/Desktop/temp/"+ attach.getUploadPath()+"/s_"+attach.getUuid()+"_"+attach.getFileName());

                    Files.delete(thumbnail);
                }
            } catch (IOException e) {
                log.error("delete file error" + e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
