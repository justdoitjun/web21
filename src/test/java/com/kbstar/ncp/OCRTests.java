package com.kbstar.ncp;

import com.kbstar.util.OCRUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

@Slf4j
@SpringBootTest
class OCRTests {

	@Value("/Users/junhyeokchoi/spring/uimg/")
	String imgpath;

	@Test
	void contextLoads() {
		JSONObject jsonObject = null;
		jsonObject = (JSONObject) OCRUtil.getResult(imgpath,"biz2.jpg");
		log.info(jsonObject.toJSONString());
		Map map = OCRUtil.getData(jsonObject);

	}

}




