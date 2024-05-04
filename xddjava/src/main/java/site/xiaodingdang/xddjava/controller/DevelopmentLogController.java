package site.xiaodingdang.xddjava.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.service.DevelopmentLogService;
import site.xiaodingdang.xddjava.service.UploadService;

@RestController
@Slf4j
public class DevelopmentLogController {
    @Autowired
    private DevelopmentLogService developmentLogService;

    @GetMapping("/website/blog")
    public ResponseResult<Object> getWebsiteBlogList() {
        return developmentLogService.getWebsiteBlogList();
    }
    @GetMapping("/website/blog/detail")
    public ResponseResult<Object> getMarkdownById(@RequestParam("id") int id){
        return developmentLogService.getMarkdownById(id);
    }
}
