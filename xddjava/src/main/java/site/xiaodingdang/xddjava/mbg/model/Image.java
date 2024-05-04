package site.xiaodingdang.xddjava.mbg.model;

import java.io.Serializable;
import java.util.Date;

public class Image implements Serializable {
    private Integer id;

    private Integer blogid;

    // 图片文件名
    private String filename;

    // 存储路径
    private String filepath;

    // 图片上传时间
    private Date createdat;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogid() {
        return blogid;
    }

    public void setBlogid(Integer blogid) {
        this.blogid = blogid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", blogid=").append(blogid);
        sb.append(", filename=").append(filename);
        sb.append(", filepath=").append(filepath);
        sb.append(", createdat=").append(createdat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}