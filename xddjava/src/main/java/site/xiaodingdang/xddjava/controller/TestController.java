package site.xiaodingdang.xddjava.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.xiaodingdang.xddjava.common.api.ResponseResult;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/trace")
    public ResponseResult<String> trace() {
        log.trace("这是一个trace消息");
        return ResponseResult.ok("trace消息");
    }

    @GetMapping("/debug")
    public ResponseResult<String> debug() {
        log.debug("这是一个debug消息");
        return ResponseResult.ok("debug消息");
    }

    @GetMapping("/info")
    public ResponseResult<String> info() {
        log.info("这是一个info消息");
        return ResponseResult.ok("info 消息");
    }

    @GetMapping("/warn")
    public ResponseResult<String> warn() {
        log.warn("这是一个warn消息");
        return ResponseResult.ok("warn 消息");
    }

    @GetMapping("/error")
    public ResponseResult<String> error() {
        log.error("这是一个error消息");
        return ResponseResult.ok("error 消息");
    }
}
