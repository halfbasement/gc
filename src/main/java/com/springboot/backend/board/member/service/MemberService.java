package com.springboot.backend.board.member.service;

import com.springboot.backend.board.member.domain.MemberDto;
import com.springboot.backend.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public List<MemberDto> listMember() {
        return memberRepository.selectAllMemberList();
    }

    public String addMember(MemberDto memberDto) {
        //중복 검증
        Optional<MemberDto> byId = memberRepository.findById(memberDto.getMemberId());
        byId.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        });
        memberRepository.insertMember(memberDto);

        return memberDto.getMemberId();
    }

    public MemberDto findById(String id){
        Optional<MemberDto> byId =  memberRepository.findById(id);
        byId.orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원 입니다 ID="+id));

        return byId.get();
    }

    public String updateMember(MemberDto memberDto){
        memberRepository.updateMember(memberDto);

        return memberDto.getMemberId()+"수정";
    }

    public MemberDto login(MemberDto memberDto){ //아이디와 비번이 일치하면 값을 뿌려줌
        Optional<MemberDto> member =  memberRepository.loginById(memberDto);

        member.orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원 입니다."));


        return member.get();
    }

    public String removeMember(MemberDto memberDto){

        memberRepository.deleteMember(memberDto.getMemberId());

        return memberDto.getMemberId()+"삭제";
    }

}
