package com.example.demo.web_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        exposeDirectory("auction-photos",registry);
    }


    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry){
        Path uploadDir= Paths.get(dirName);
        String uploadPath=uploadDir.toFile().getAbsolutePath();
        
        if(dirName.startsWith("../")){dirName.replace("../", "");}

        registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/" + uploadPath + "/");
    }
}
