package com.springboot.backend.board.member.controller;

import com.springboot.backend.board.member.domain.MemberDto;
import com.springboot.backend.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/member")
    public String save(@RequestBody MemberDto memberDto){
    return memberService.addMember(memberDto);
    }

    @DeleteMapping("/member")
    public String delete(@RequestBody MemberDto memberDto){
        return memberService.removeMember(memberDto);
    }

    @PutMapping("/member")
    public String update(@RequestBody MemberDto memberDto){
        return memberService.updateMember(memberDto);
    }



}
