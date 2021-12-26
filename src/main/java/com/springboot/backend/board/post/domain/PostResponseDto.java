package com.springboot.backend.board.post.domain;

import com.springboot.backend.board.common.file.UploadResultDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private Date postWriteDate;
    private String memberId;

    private List<PostFileDto> fileList;
}
