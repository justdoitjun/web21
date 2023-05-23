package com.kbstar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${imgdir}")
    String imgdir;
    @Value("${logdir}")
    String logdir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations(imgdir);
        registry.addResourceHandler("/logs/**").addResourceLocations(logdir);
       // registry.addResourceHandler("/uimg/**").addResourceLocations(imgpath);
        // 이 프로젝트에 있는 logs라는 폴더에 있는 로그파일을 리소스로 쓰겠다. 언제? logdir이라고 나오면 말이지.
    }

}
