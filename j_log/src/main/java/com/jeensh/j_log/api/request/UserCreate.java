package com.jeensh.j_log.api.request;


import lombok.Data;

@Data
public class UserCreate {
    private String name;

    private String email;

    private String password;

    private String passwordCheck;
}
