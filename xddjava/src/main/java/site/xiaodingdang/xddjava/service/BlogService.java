package site.xiaodingdang.xddjava.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.xiaodingdang.xddjava.common.api.ResponseResult;
import site.xiaodingdang.xddjava.mbg.mapper.BlogMapper;
import site.xiaodingdang.xddjava.mbg.mapper.ImageMapper;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.BlogExample;
import site.xiaodingdang.xddjava.mbg.model.Image;
import site.xiaodingdang.xddjava.mbg.model.ImageExample;

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

@Service
@Slf4j
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Value("${xdd.path.image.location}")
    private String imagePathLocation;
    @Value("${xdd.path.image.http}")
    private String imagePathHttp;

    /**
     * 删除所有博客,供测试使用的,方便初始化
     */
    public ResponseResult<Object> deleteAllBlog(){
        List<Blog> blogList = blogMapper.selectByExample(new BlogExample());
        for(Blog blog: blogList){
            deleteBlogAndImageById(blog.getId());
        }
        return ResponseResult.success("删除所有博客成功");
    }
    /**
     * 更新博客,对接controller
     */
    public ResponseResult<Object> updeteBlogById(int id, MultipartFile file){
        Blog blog = blogMapper.selectByPrimaryKey(id);
        deleteBlogAndImageById(id);
        addMarkdown(file,blog.getSort(),blog.getCreatedAt());
        return ResponseResult.success("更新成功");
    }
    /**
     * 删除博客还应该删除它的引用的图片,因为更新接口的需要,它还需要返回删掉的那个Blog对象,或许它不应该直接对接controller
     */
    public ResponseResult<Object> deleteBlogAndImageById(int id){
        ImageExample imageExample = new ImageExample();
        imageExample.createCriteria().andBlogidEqualTo(id);
        List<Image> imageList = imageMapper.selectByExample(imageExample);
        imageList.forEach(image -> {
            FileUtil.del(image.getFilepath());
            imageMapper.deleteByPrimaryKey(image.getId());
        });
        blogMapper.deleteByPrimaryKey(id);
        return ResponseResult.success("删除成功");
    }
    /**
     * 上传博客,其中不少地方都固定为了网站开发日志,xdd等字符串
     */
    public ResponseResult<Object> addMarkdown(MultipartFile file, int sort, Date createAt){
        // 通过文件扩展名判断是不是zip文件
        String uploadFileName = file.getOriginalFilename();
        // 如果不是返回状态码400
        if (uploadFileName != null && uploadFileName.toLowerCase().endsWith(".zip")) {
            try {
                // 创建临时文件夹
                Path tempDirectory = Files.createTempDirectory("tempMarkdownZip");
                log.debug("tempDirectory: "+tempDirectory.toString());
                // 将上传文件解压到临时目录
                try (InputStream inputStream = file.getInputStream()) {
                    try {
                        ZipUtil.unzip(inputStream,tempDirectory.toFile(), Charset.forName("GBK"));
                    }catch (Exception e){
                        ZipUtil.unzip(inputStream,tempDirectory.toFile(), StandardCharsets.UTF_8);
                        e.printStackTrace();
                    }
                }
                // 有几个markdown文件下面的列表就会有几个元素
                List<Map<String, String>> mdFileContents = GetTitleAndContents(tempDirectory);
                for (Map<String, String> mdContent : mdFileContents) {
                    Blog blog = new Blog();
                    // 作者先固定为小叮当
                    blog.setAuthor("小叮当");
                    // 标题为文件名
                    blog.setTitle(mdContent.get("title"));
                    // 标签先固定为网站开发日志
                    blog.setTags("网站开发日志");
                    blog.setCreatedAt(createAt);
                    blog.setUpdatedAt(new Date());
                    // 设置排序值
                    if(sort==-1){
                        BlogExample blogExample = new BlogExample();
                        blogExample.createCriteria().andTagsEqualTo("网站开发日志");
                        List<Blog> blogList = blogMapper.selectByExample(blogExample);
                        blog.setSort(blogList.size());
                    }else{
                        blog.setSort(sort);
                    }
                    Map<String, Object> result = processImageReferences(mdContent.get("content"), Paths.get(mdContent.get("parentPath")));
                    // 内容为修改图片引用后的markdown内容
                    blog.setContent((String) result.get("content"));
                    blogMapper.insertSelective(blog);
                    for(String accessPath: (List<String>)result.get("accessImagePaths")){
                        Image image = new Image();
                        // 外键,为了以后删除博客用,因为删除markdown文件的话自然也要删除图片
                        image.setBlogid(blog.getId());
                        // 图片的保存路径
                        image.setFilepath(accessPath);
                        imageMapper.insertSelective(image);
                    }
                }
                // 删除临时文件夹及其内容
                FileUtils.deleteDirectory(tempDirectory.toFile());
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
     * map中存有title, content, parentPath三个键,title是去掉扩展名的文件名,content则是文件内容,parentPath是文件的父路径
     */
    private List<Map<String, String>> GetTitleAndContents(Path tmpFilePath) throws IOException {
        List<Map<String, String>> mdFileContents = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(tmpFilePath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".md"))
                    .forEach(mdFilePath -> {
                        try {
                            String mdContent = Files.readString(mdFilePath);
                            Map<String, String> map = new HashMap<>();
                            map.put("title", site.xiaodingdang.xddjava.common.util.FileUtil.removeFileExtension(mdFilePath.getFileName().toString()));
                            map.put("content", mdContent);
                            map.put("parentPath",mdFilePath.getParent().toString());
                            mdFileContents.add(map);
                        } catch (IOException e) {
                            // Handle exception
                            e.printStackTrace();
                        }
                    });
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
    private Map<String, Object> processImageReferences(String mdContent, Path directoryPath) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> accessImagePaths = new ArrayList<>();
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(mdContent);
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            Path imagePath = directoryPath.resolve(imageUrl);
            if (Files.exists(imagePath) && !Files.isDirectory(imagePath)) {
                String newImageName = UUID.randomUUID().toString() + "." + site.xiaodingdang.xddjava.common.util.FileUtil.getFileExtension(imageUrl);
                String accessImagePath = imagePathHttp + "/" + newImageName;
                Path newImagePath = Paths.get(imagePathLocation, newImageName);
                Files.copy(imagePath, newImagePath, StandardCopyOption.REPLACE_EXISTING);
                accessImagePaths.add(newImagePath.toString());
                // 修改mdContent的url为accessImagePath
                mdContent = mdContent.replace(imageUrl, accessImagePath);
            }
        }
        resultMap.put("content", mdContent);
        resultMap.put("accessImagePaths", accessImagePaths);
        return resultMap;
    }
}
