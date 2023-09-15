package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.request.PostEdit;
import com.jeensh.j_log.api.request.PostSearch;
import com.jeensh.j_log.api.response.PostResponse;

import java.util.List;

public interface PostService {

    /**
     * post 저장
     * 반환값 : postId
     */
    Long write(PostCreate postCreate);

    /**
     * 단건조회
     * 반환값 : PostResponse(조회한 post)
     */
    PostResponse get(Long id);

    /**
     * 페이지 조회
     * 반환값 : List<PostResponse>(조회한 post 목록)
     */
    List<PostResponse> getList(PostSearch postSearch);

    /**
     * Post 수정
     * 반환값 : PostResponse(수정 후 post)
     */
    PostResponse edit(Long id, PostEdit postEdit);

    /**
     * post 삭제
     */
    void delete(Long id);
}
