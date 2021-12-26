package com.springboot.backend.board.common.file;

import com.springboot.backend.board.post.domain.PostResponseDto;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.codehaus.groovy.tools.shell.IO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>>uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDto> resultDtoList = new ArrayList<>();


        for(MultipartFile uploadFile : uploadFiles){

            //1. 파일 형식 검사
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("이미지 파일이 아닙니다");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String fileName = uploadFile.getOriginalFilename();

            log.info("파일 이름 : "+fileName);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try{

                uploadFile.transferTo(savePath);

                //썸네일
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator +"s_" + uuid + "_" + fileName;

                File thumbnailFile = new File(thumbnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);


                resultDtoList.add(new UploadResultDto(fileName,uuid,folderPath));

            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(resultDtoList,HttpStatus.OK);
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){

        String srcFileName= null;
        try{
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_"+file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            log.error("파일삭제에러");
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            File file = new File(uploadPath+File.separator+srcFileName);

            HttpHeaders header = new HttpHeaders();

            log.info("-------file.topath----------"+file.toPath());

            header.add("Content-Type",Files.probeContentType(file.toPath()));

            log.info("---------file-------"+file);

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);


        }catch (Exception e){
          //  log.error(e.getMessage());
            log.error("디스플레이오류");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    private String makeFolder(){
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/",File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);

        if(uploadPathFolder.exists()==false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
