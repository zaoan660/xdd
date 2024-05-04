package site.xiaodingdang.xddjava.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.service.BlogService;

@RestController
@Slf4j
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/blog/delete")
    public ResponseResult<Object> deleteBlogById(@RequestParam("id") int id){
        return blogService.deleteBlogAndImageById(id);
    }
    @PostMapping("/blog/update")
    public ResponseResult<Object> updateBlogById(@RequestParam("id") int id, @RequestParam("file") MultipartFile file){
        return blogService.updeteBlogById(id, file);
    }
    @PostMapping("/blog/deleteAll")
    public ResponseResult<Object> deleteAllBlog(){
        return blogService.deleteAllBlog();
    }
}
