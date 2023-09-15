package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.request.PostEdit;
import com.jeensh.j_log.api.request.PostSearch;
import com.jeensh.j_log.api.response.PostResponse;

import java.util.List;

public interface PostService {

    /**
     * post 저장
     */
    Long write(PostCreate postCreate);

    /**
     * 단건조회
     */
    PostResponse get(Long id);

    /**
     * 페이지 조회
     */
    List<PostResponse> getList(PostSearch postSearch);

    /**
     * Post 수정
     */
    PostResponse edit(Long id, PostEdit postEdit);

    /**
     * post 삭제
     */
    void delete(Long id);
}
