package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@Log4j
public class UploadController {

    @GetMapping("/uploadForm")
    public void uploadForm(){
        //log.info("upload Form...");
        System.out.println("upload Form...");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

        String uploadFolder ="/Users/yhs/Desktop/temp/upload";

        for(MultipartFile multipartFile : uploadFile) {
            System.out.println("------------------");
            System.out.println("Upload File Name : " +multipartFile.getOriginalFilename());
            System.out.println("Upload File Size : " +multipartFile.getSize());

            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

            try{
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            } // end catch
        }//end for

    }

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        //System.out.println(file.toPath());
        //기존 방법이 안 먹혀서 다른 방법 씀
        String contentType = URLConnection.guessContentTypeFromName(String.valueOf(file.toPath()));

        return  contentType.startsWith("image");
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {

        System.out.println("upload Ajax...");
    }

    @GetMapping("/uploadAjaxAction")
    public void uploadAjaxGet(){
        System.out.println("upload Ajax Get...");
    }

    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

        List<AttachFileDTO> list = new ArrayList<>();
        String uploadFolder = "/Users/yhs/Desktop/temp/upload";

        String uploadFolderPath = getFolder();

        //make Folder --------
        File uploadPath = new File(uploadFolder, uploadFolderPath);


        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        } //make yyyy/MM/dd

        for(MultipartFile multipartFile : uploadFile) {

            AttachFileDTO attachFileDTO = new AttachFileDTO();
            String uploadFileName = multipartFile.getOriginalFilename();


            System.out.println("only file name : " + uploadFileName);
            attachFileDTO.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();
            uploadFileName = uuid.toString()+"_" +uploadFileName;

            try{
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);

                attachFileDTO.setUuid(uuid.toString());
                attachFileDTO.setUploadPath(uploadFolderPath);

                //체크이미지 밖
                //attachFileDTO.setImage(true);

                //FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
                //Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100) ;
                //thumbnail.close();

                //check image type file
                if(checkImageType(saveFile)){

                    attachFileDTO.setImage(true);

                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100) ;
                    thumbnail.close();
                }

                //add to List
                list.add(attachFileDTO);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }// end for
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) throws IOException {
        System.out.println("fileName : " + fileName);
        File file = new File("/Users/yhs/Desktop/temp/" +fileName);
        System.out.println("file");

        ResponseEntity<byte[]> result = null;

        HttpHeaders header = new HttpHeaders();

        header.add("Content-Type", URLConnection.guessContentTypeFromName(String.valueOf(file.toPath())));
        result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        return result;
    }

    @GetMapping(value = "/download" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
        System.out.println("download file : " + fileName);

        Resource resource = new FileSystemResource("/Users/yhs/Desktop/temp/upload/"+fileName);

        if(resource.exists() == false) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("resource : " + resource);

        String resourceName = resource.getFilename();

        //remove UUID
        String resourceOriginName = resourceName.substring(resourceName.indexOf("_")+1);

        HttpHeaders headers = new HttpHeaders();

        try{
            String downloadName = null;
            if(userAgent.contains("Trident")){
                System.out.println("IE browser");
                downloadName = URLEncoder.encode(resourceOriginName, "UTF-8"). replaceAll("\\+", "");
            } else if(userAgent.contains("Edge")) {
                System.out.println("Edge Browser");
                downloadName = URLEncoder.encode(resourceOriginName, "UTF-8");
                System.out.println("Edge name : "+downloadName);
            } else {
                System.out.println("Chrome Browser");
                downloadName = new String(resourceOriginName.getBytes("UTF-8"), "ISO-8859-1");
            }
            headers.add("Content-Disposition", "attachment; filename=" + downloadName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }


    @PostMapping("/deleteFile")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteFile(String fileName, String type) {
        System.out.println("deleteFile : " + fileName);
        File file;

        try {
            file = new File("/Users/yhs/Desktop/temp/upload/" + URLDecoder.decode(fileName, "UTF-8"));

            file.delete();

            if(type.equals("image")){
                String largeFileName = file.getAbsolutePath().replace("s_", "");
                System.out.println("largeFileName : "+ largeFileName);
                file = new File(largeFileName);
                file.delete();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

}
