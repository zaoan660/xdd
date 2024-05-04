package site.xiaodingdang.xddjava.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.BlogExample;

@Mapper
public interface BlogMapper {
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blog row);

    int insertSelective(Blog row);

    List<Blog> selectByExampleWithBLOBs(BlogExample example);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Blog row, @Param("example") BlogExample example);

    int updateByExampleWithBLOBs(@Param("row") Blog row, @Param("example") BlogExample example);

    int updateByExample(@Param("row") Blog row, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog row);

    int updateByPrimaryKeyWithBLOBs(Blog row);

    int updateByPrimaryKey(Blog row);
}