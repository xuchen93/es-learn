package com.github.xuchen93.es.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuchen.wang
 * @date 2023/9/28
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        log.info("hello");
        return "success";
    }
}
