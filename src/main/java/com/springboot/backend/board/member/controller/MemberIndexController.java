package com.springboot.backend.board.member.controller;

import com.springboot.backend.board.member.domain.MemberDto;
import com.springboot.backend.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MemberIndexController {
    private final MemberService memberService;

    @GetMapping("/member")
    public String listMembers(Model model, HttpServletRequest request) {


        HttpSession session = request.getSession(false);

        MemberDto member = (MemberDto) session.getAttribute("memberInfo");

        log.info(member);

        String memberId = member.getMemberId();

        if (memberId != null && memberId.equals("admin")) {

            List memberList = memberService.listMember();

            model.addAttribute("memberList", memberList);

            return "member/member";
        } else {
            return "redirect:/";
        }


    }

    @GetMapping("/member/create")
    public String createForm() {
        return "member/member-create";
    }

    @GetMapping("/member/{id}")
    public String detailMember(@PathVariable String id, Model model) {
        MemberDto member = memberService.findById(id);

        model.addAttribute("member", member);
        return "member/member-update";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "member/member-login";
    }

    @PostMapping("/member/login")
    public String login(@RequestBody MemberDto memberDto, HttpServletRequest request, Model model) {
        MemberDto loginMember = memberService.login(memberDto);

        if (loginMember != null) {
            HttpSession session = request.getSession();
            session.setAttribute("memberInfo", loginMember);
            model.addAttribute("session", session);
        }

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logOut(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.invalidate();

        return "redirect:/";
    }




}
