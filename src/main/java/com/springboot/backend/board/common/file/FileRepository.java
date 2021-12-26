package com.springboot.backend.board.common.file;

import com.springboot.backend.board.post.domain.PostFileDto;
import com.springboot.backend.board.post.domain.PostResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileRepository {
    void insertFile(PostFileDto postFileDto);
    List<PostFileDto> findFile(Long no);
}
