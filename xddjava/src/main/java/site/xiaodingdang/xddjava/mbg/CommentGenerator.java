package site.xiaodingdang.xddjava.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 *  MyBatis Generator 的注释生成器,其实添加注释,注解,或者往文件里填一些代码都可以做到
 * 注释分为两部分,一部分是添加 import org.apache.ibatis.annotations.Mapper 和 model字段的注释
 * 另一部分和 swagger 有关,我注释掉了,先不用管.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX="Example";
    private static final String MAPPER_SUFFIX="Mapper";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME="io.swagger.annotations.ApiModelProperty";
    private static final String mapper_class_path="org.apache.ibatis.annotations.Mapper";

    /**
     * 设置用户配置的参数. 就是得到并设置之前xml里写的一些配置
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释和与swagger相关的注解
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加swagger注解信息
        if(addRemarkComments&&StringUtility.stringHasValue(remarks)){
            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
//            if(remarks.contains("\"")){
//                remarks = remarks.replace("\"","'");
//            }
//            //给model的字段添加swagger注解
//            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
        }
    }

    /**
     * 在addFieldComment里调用了,生成model里字段的注释
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        //获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for(String remarkLine:remarkLines){
            field.addJavaDocLine("// "+ remarkLine);
        }
        // 用来添加在每个注释下都加上 @mbg.generated ,只是作为"这是一个自动生成的注释"的标记
        // 如果还是不明白可以把这段取消注释再运行一遍
//        addJavadocTag(field, false);
    }

    /**
     * 为生成的文件添加一些 import 语句
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
//        //只在model中添加swagger注解类的导入
//        if(!compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX)&&!compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
//            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
//        }
        if(compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(mapper_class_path));
        }
    }
}
