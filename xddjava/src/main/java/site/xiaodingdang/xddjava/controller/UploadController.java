package site.xiaodingdang.xddjava.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.mbg.mapper.BlogMapper;
import site.xiaodingdang.xddjava.mbg.mapper.ImageMapper;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.Image;
import site.xiaodingdang.xddjava.service.UploadService;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@RestController
@Slf4j
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload/markdown")
    public ResponseResult<Object> uploadMarkdown(@RequestParam("file") MultipartFile file) {
        return uploadService.handleMarkdownUpload(file);
    }
}
