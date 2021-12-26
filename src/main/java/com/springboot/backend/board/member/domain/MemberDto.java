package com.springboot.backend.board.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MemberDto {
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberEmail;
    private Date memberJoinDate;


}
