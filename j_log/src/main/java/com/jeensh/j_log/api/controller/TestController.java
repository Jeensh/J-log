package com.jeensh.j_log.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Profile("test")
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

}
