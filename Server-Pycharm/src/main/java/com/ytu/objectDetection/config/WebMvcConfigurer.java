package com.ytu.objectDetection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的img目录下，访问路径如：http://localhost:8081/file/1.jpg
        //其中file表示访问的前缀。"file:E:/PyCharm_workspace/yolov3-tf2-master-2/data"是文件真实的存储路径
        registry.addResourceHandler("/file/**").addResourceLocations("file:C:/Users/muxiaoyu/Desktop/Leeds/Project/reference_code/test/data/")
                .addResourceLocations("file:C:/Users/muxiaoyu/Desktop/Leeds/Project/reference_code/test/");
        super.addResourceHandlers(registry);
    }
}
