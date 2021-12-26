package com.springboot.backend.board.post.repository;

import com.springboot.backend.board.post.domain.PostFileDto;
import com.springboot.backend.board.post.domain.PostResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {
    List<PostResponseDto> selectAllPostList();
    Optional<PostResponseDto> findByNo(Long no); //글 번호로 값 검증 하는건 어느 dto를 사용할 지 모름 // findByNo의 resultType 정해져있어서 사용 불가능

    List<PostResponseDto> findByMemberId(String memberId);
    void insertPost(PostResponseDto responseDto);
    void deletePost(PostResponseDto responseDto);
    void updatePost(PostResponseDto responseDto);
    Long findMaxNo();
}
