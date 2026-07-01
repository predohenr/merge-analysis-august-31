package com.baomidou.mybatisplus.test.h2;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.h2.mapper.H2User2Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:h2/spring-test-h2.xml"})
class H2User2Test extends BaseTest {
    @Autowired
    private H2User2Mapper h2User2Mapper;

    @Test
    void testLambdaTypeHandler() {
        List<H2User2> h2User2List = h2User2Mapper.selectList(Wrappers.<H2User2>lambdaQuery()
            .eq(H2User2::getName, "{\"id\":101,\"name\":\"Tomcat\"}"));
        Assertions.assertEquals(1, h2User2List.size());
        Assertions.assertEquals("Tomcat OK", h2User2List.get(0).getName());
    }
}
