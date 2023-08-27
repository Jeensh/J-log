package com.jeensh.j_log.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요!")
    private String title;
    @NotBlank(message = "컨텐츠를 입력해주세요!")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostCreate() {
    }
}
