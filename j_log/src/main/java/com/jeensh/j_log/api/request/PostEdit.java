package com.jeensh.j_log.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PostEdit {
    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content){
        this.title = title;
        this.content = content;
    }

    public PostEdit() {
    }
}