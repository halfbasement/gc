package com.springboot.backend.board.member.repository;

import com.springboot.backend.board.member.domain.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {
    List<MemberDto> selectAllMemberList();

    void insertMember(MemberDto memberDto);

    void deleteMember(String memberId);

    void updateMember(MemberDto memberDto);

    Optional<MemberDto> findById(String memberId);

    Optional<MemberDto> loginById(MemberDto memberDto);

}
