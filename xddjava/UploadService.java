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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class UploadService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Value("${xdd.path.image.location}")
    private String imagePathLocation;
    @Value("${xdd.path.image.http}")
    private String imagePathHttp;

    /**
     * 公共方法.把上传的zip文件处理后保存到数据库
     */
    public ResponseResult<Object> handleMarkdownUpload(MultipartFile file) {
        // 通过文件扩展名判断是不是zip文件
        String uploadFileName = file.getOriginalFilename();
        // 如果不是返回状态码400
        if (uploadFileName != null && uploadFileName.toLowerCase().endsWith(".zip")) {
            try {
                // 创建临时文件夹
                Path tempDirectory = Files.createTempDirectory("tempMarkdownZip");
                log.debug("tempDirectory: "+tempDirectory.toString());
                // 将上传的文件保存到临时文件夹中
//                Path zipFilePath = tempDirectory.resolve(uploadFileName);
//                log.debug("zipFilePath: "+ zipFilePath);
                try (InputStream inputStream = file.getInputStream()) {
                    try {
                        ZipUtil.unzip(inputStream,tempDirectory.toFile(), Charset.forName("GBK"));
                    }catch (Exception e){
                        ZipUtil.unzip(inputStream,tempDirectory.toFile(), StandardCharsets.UTF_8);
                        e.printStackTrace();
                    }
//                    Files.copy(inputStream, zipFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
                // 有几个markdown文件下面的列表就会有几个元素
//                List<Map<String, String>> mdFileContents = GetTitleAndContentsByUnzip(zipFilePath);
//                for (Map<String, String> mdContent : mdFileContents) {
//                    Blog blog = new Blog();
//                    // 作者先固定为小叮当
//                    blog.setAuthor("小叮当");
//                    // 标题为文件名
//                    blog.setTitle(mdContent.get("title"));
//                    Map<String, Object> result = processImageReferences(mdContent.get("content"), zipFilePath);
//                    // 内容为修改图片引用后的markdown内容
//                    blog.setContent((String) result.get("content"));
//                    blogMapper.insertSelective(blog);
//                    for(String accessPath: (List<String>)result.get("accessImagePaths")){
//                        Image image = new Image();
//                        // 外键,为了以后删除博客用,因为删除markdown文件的话自然也要删除图片
//                        image.setBlogid(blog.getId());
//                        // 图片的保存路径
//                        image.setFilepath(accessPath);
//                        imageMapper.insertSelective(image);
//                    }
//                }
//                // 删除临时文件夹及其内容
//                FileUtils.deleteDirectory(tempDirectory.toFile());
                return ResponseResult.success("Markdown 文件上传并处理成功");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseResult.error("文件处理失败：" + e.getMessage());
            }
        }
        return ResponseResult.error("上传的文件不是 ZIP 文件");
    }

    /**
     * 解压缩zip文件并返回List<Map<String, String>>,一个md文件对应一个map
     * map中存有title和content两个键,title是去掉扩展名的文件名,content则是文件内容
     */
    private List<Map<String, String>> GetTitleAndContentsByUnzip(Path zipFilePath) throws IOException {
        List<Map<String, String>> mdFileContents = new ArrayList<>();
        String desDirectory = "/home/lighthouse";


        try (ZipFile zipFile = new ZipFile(zipFilePath.toFile(), Charset.forName("GBK"))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".md")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        String mdContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        Map<String, String> map = new HashMap<>();
                        map.put("title", FileUtil.removeFileExtension(entry.getName()));
                        map.put("content",mdContent);
                        mdFileContents.add(map);
                    }
                }
            }
        }
        return mdFileContents;
    }

    /**
     * 它完成两个任务
     * 1. 保存markdown文件中提到的图片到本地目录
     * 2. 修改markdown文件中对图片的引用为http的方式
     * 它返回map,map有两个键
     * content 对应String类型,指修改后的markdown内容
     * accessImagePaths 对应List<String>,指被保存的图片文件名,这些文件名已经使用UUID重命名了
     */
    private Map<String, Object> processImageReferences(String mdContent, Path zipDirectory) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> accessImagePaths = new ArrayList<>();
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(mdContent);
        try (ZipFile zipFile = new ZipFile(zipDirectory.toFile(), Charset.forName(System.getProperty("sun.jnu.encoding")))) {
            while (matcher.find()) {
                String imageUrl = matcher.group(1);
                ZipEntry entry = zipFile.getEntry(imageUrl);
                if (entry != null)  {
                    String newImageName = UUID.randomUUID().toString() + "."+FileUtil.getFileExtension(imageUrl);
                    String accessImagePath = imagePathHttp+"/"+newImageName;
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        Path newImagePath = Paths.get(imagePathLocation, newImageName);
                        Files.copy(inputStream, newImagePath, StandardCopyOption.REPLACE_EXISTING);
                        accessImagePaths.add(accessImagePath);
                        // 修改mdContent的url为accessImagePath
                        mdContent = mdContent.replace(imageUrl, accessImagePath);
                    }
                }
            }
        }
        resultMap.put("content",mdContent);
        resultMap.put("accessImagePaths",accessImagePaths);
        return resultMap;
    }
}
