package com.springboot.backend.board.post.controller;

import com.springboot.backend.board.member.domain.MemberDto;
import com.springboot.backend.board.post.domain.PostResponseDto;
import com.springboot.backend.board.post.service.PostService;
import com.sun.org.apache.xpath.internal.operations.Mult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

@Log4j2
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;




    @PutMapping("/post/{no}")
    public Long updatePost(@PathVariable Long no, @RequestBody PostResponseDto responseDto) {
        postService.updatePost(no, responseDto);
        return no;
    }

    @DeleteMapping("/post/{no}")
    public Long deletePost(@PathVariable Long no) {

        postService.removePost(no);

        return no;
    }



}
