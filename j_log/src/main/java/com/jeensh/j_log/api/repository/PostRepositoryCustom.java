package com.jeensh.j_log.api.repository;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
