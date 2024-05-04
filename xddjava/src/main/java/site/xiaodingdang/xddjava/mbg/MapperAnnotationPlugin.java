package site.xiaodingdang.xddjava.mbg;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;

import java.util.List;

/**
 * 这是一个 MyBatis Generator 插件，用于在生成的 Mapper 接口中添加 @Mapper 注解
 * clientGenerated 在生成 Mapper 接口后调用, validate 没什么特殊情况返回 true 就可以
 */
public class MapperAnnotationPlugin extends PluginAdapter {
    private static final String MAPPER_SUFFIX = "Mapper";

    @Override
    public boolean clientGenerated(Interface anInterface, IntrospectedTable introspectedTable) {
        String mapperName = anInterface.getType().getShortName();
        if (mapperName.endsWith(MAPPER_SUFFIX)) {
            anInterface.addAnnotation("@Mapper");
        }
        return super.clientGenerated(anInterface, introspectedTable);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}

