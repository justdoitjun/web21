package com.kbstar.ncp;

import com.kbstar.util.CFRCelebrityUtil;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CFRCelebrityTests {
    @Value("/Users/junhyeokchoi/spring/uimg/")
    String imgpath;
    @Autowired
    CFRCelebrityUtil celebrityUtil;

    @Test
    void contextLoads() throws Exception {
        String imgname ="ma.jpg";
        JSONObject result = (JSONObject) celebrityUtil.getResult(imgpath,imgname);
        log.info(String.valueOf(result));
    }
}
