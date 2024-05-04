package site.xiaodingdang.xddjava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.xiaodingdang.xddjava.mbg.mapper.BlogMapper;
import site.xiaodingdang.xddjava.mbg.model.Blog;
import site.xiaodingdang.xddjava.mbg.model.BlogExample;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class XddjavaApplicationTests {
//    @Autowired
//    private BlogMapper blogMapper;

    @Test
    void contextLoads() {
        List<String> stringList = new ArrayList<>();
        System.out.println(stringList.size());
    }
}

