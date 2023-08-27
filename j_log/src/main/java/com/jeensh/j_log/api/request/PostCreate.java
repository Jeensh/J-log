package com.jeensh.j_log.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요!")
    private String title;
    @NotBlank(message = "컨텐츠를 입력해주세요!")
    private String content;

    @Override
    public String toString() {
        return "title=" + title + ", content=" + content;
    }
}
