package com.study.cloud.test;

import com.study.cloud.utils.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private RedisUtil redisUtil;

    @org.junit.Test
    public void redName(){
        redisUtil.set("name","pxf");
    }

}