package site.xiaodingdang.xddjava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.mbg.mapper.BlogMapper;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.BlogExample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DevelopmentLogService {
    @Autowired
    private BlogMapper blogMapper;

    /**
     * 我需要它返回一个json,json有一个列表,列表的有两个字段: createAt日期,title文件名,用来做文件树
     */
    public ResponseResult<Object> getWebsiteBlogList(){
        List<Map<String, String>> blogList = new ArrayList<>();
        BlogExample blogExample = new BlogExample();
        List<Blog> blogs = blogMapper.selectByExample(blogExample);
        for(Blog blog: blogs){
            Map<String, String> map = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("id",blog.getId().toString());
            map.put("createAt", sdf.format(blog.getCreatedAt()));
            map.put("title",blog.getTitle());
            blogList.add(map);
        }
        return ResponseResult.ok(blogList);
    }
    /**
     * 我需要它在传入id后得到markdown文件的内容
     */
    public ResponseResult<Object> getMarkdownById(int id){
        Blog blog = blogMapper.selectByPrimaryKey(id);
        return ResponseResult.ok(blog.getContent());
    }
}
