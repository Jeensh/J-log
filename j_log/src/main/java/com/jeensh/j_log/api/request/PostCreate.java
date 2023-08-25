package com.jeensh.j_log.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class PostCreate {
    public String title;
    public String content;

    @Override
    public String toString() {
        return "title=" + title + ", content=" + content;
    }
}
