package site.xiaodingdang.xddjava.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.xiaodingdang.xddjava.mbg.model.Image;
import site.xiaodingdang.xddjava.mbg.model.ImageExample;

@Mapper
public interface ImageMapper {
    long countByExample(ImageExample example);

    int deleteByExample(ImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Image row);

    int insertSelective(Image row);

    List<Image> selectByExample(ImageExample example);

    Image selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Image row, @Param("example") ImageExample example);

    int updateByExample(@Param("row") Image row, @Param("example") ImageExample example);

    int updateByPrimaryKeySelective(Image row);

    int updateByPrimaryKey(Image row);
}