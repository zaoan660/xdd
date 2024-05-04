package site.xiaodingdang.xddjava.service;

import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.common.util.FileUtil;
import site.xiaodingdang.xddjava.mbg.mapper.BlogMapper;
import site.xiaodingdang.xddjava.mbg.mapper.ImageMapper;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.BlogExample;
import site.xiaodingdang.xddjava.mbg.model.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class UploadService {
    @Autowired
    private BlogService blogService;

    /**
     * 公共方法.把上传的zip文件处理后保存到数据库
     */
    public ResponseResult<Object> handleMarkdownUpload(MultipartFile file) {
       return blogService.addMarkdown(file, -1, new Date());
    }

}
