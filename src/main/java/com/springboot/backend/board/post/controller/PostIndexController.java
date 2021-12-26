package com.springboot.backend.board.post.controller;

import com.springboot.backend.board.member.domain.MemberDto;
import com.springboot.backend.board.post.domain.PostResponseDto;
import com.springboot.backend.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class PostIndexController {

    private final PostService postService;



    @PostMapping("/post")
    public String savePost(PostResponseDto responseDto, HttpServletRequest request) {

        HttpSession session = request.getSession();

        MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");

        responseDto.setMemberId(memberDto.getMemberId());

        postService.addPost(responseDto);


        return "redirect:/post";
    }


    @GetMapping("/post")
    public String listPost(Model model){
        List<PostResponseDto> posts = postService.listPosts();
        model.addAttribute("posts",posts);

        return "post/post";
    }

    @GetMapping("/post/{no}")
    public String detailPost(@PathVariable Long no , Model model,HttpServletRequest request){
        PostResponseDto post =  postService.detailPost(no);

        HttpSession session =request.getSession();

        MemberDto  member = (MemberDto) session.getAttribute("memberInfo");

        if(member != null) {
            model.addAttribute("member", member);
        }
        model.addAttribute("post",post);

        return "post/post-detail";

    }

    @GetMapping("/post/post-update/{no}")
    public String updateForm(@PathVariable Long no, Model model){
        PostResponseDto post= postService.detailPost(no);

       model.addAttribute("post",post);

        return "post/post-update";
    }

    @GetMapping("/post/post-create")
    public String createForm(HttpServletRequest request){


        HttpSession session = request.getSession();//false 넣으면 세션 자체를 생성 안해서 getAttribute접근할 때 에러가 남

        MemberDto member = (MemberDto) session.getAttribute("memberInfo");

        if(member!=null){
            return "post/post-create";
        }else {
            return "member/member-login";
        }
    }
    @GetMapping("/post/mypost")
    public String listMyPost(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false); // 로그인 중 일때만 접근 가능하기 때문에 에러 x (로그인 없이 접근하면 에러남)

        MemberDto member = (MemberDto) session.getAttribute("memberInfo");

        String memberId = member.getMemberId();

        List<PostResponseDto> myPost = postService.listMyPost(memberId);

        model.addAttribute("mypost",myPost);

        return "post/post-my";
    }



}
