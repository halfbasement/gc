package com.springboot.backend.board.post.service;

import com.springboot.backend.board.common.file.FileRepository;
import com.springboot.backend.board.post.domain.PostFileDto;
import com.springboot.backend.board.post.domain.PostResponseDto;
import com.springboot.backend.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final FileRepository fileRepository;

    public List<PostResponseDto> listPosts() {
        return postRepository.selectAllPostList();
    }




    public PostResponseDto detailPost(Long no) {
        Optional<PostResponseDto> post = postRepository.findByNo(no);
        post.orElseThrow(() -> new IllegalArgumentException("해당 글은 존재하지 않습니다"));

        List<PostFileDto> fileList =  fileRepository.findFile(no);

        post.get().setFileList(fileList);

        log.info(post.get().toString());

        return post.get();
    }


    public void updatePost(Long no, PostResponseDto responseDto) {
        Optional<PostResponseDto> post = postRepository.findByNo(no);
        post.orElseThrow(() -> new IllegalArgumentException("해당 글은 존재하지 않습니다"));

        post.get().setPostTitle(responseDto.getPostTitle());
        post.get().setPostContent(responseDto.getPostContent());

          postRepository.updatePost(post.get());

    }

    public List<PostResponseDto> listMyPost(String memberId) {
        return postRepository.findByMemberId(memberId);
    }

    @Transactional
    public void addPost(PostResponseDto responseDto) {

        postRepository.insertPost(responseDto);

        Long afterInsertNo = postRepository.findMaxNo();

        if(responseDto.getFileList() == null || responseDto.getFileList().size() <=0){
            return;
        }

        responseDto.getFileList().forEach( file ->{
            file.setPostNo(afterInsertNo);
            log.info(file.getThumbnailURL());
            fileRepository.insertFile(file);
        });

    }


    public void removePost(Long no) {

        Optional<PostResponseDto> post = postRepository.findByNo(no); //옵셔널로 감싸서 값 검증
        post.orElseThrow(() -> new IllegalArgumentException("해당 글은 존재하지 않습니다"));

        postRepository.deletePost(post.get());
    }
}
