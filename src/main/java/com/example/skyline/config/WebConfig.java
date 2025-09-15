package com.example.skyline.config; // ❗️ 이 부분은 실제 프로젝트의 패키지 경로에 맞게 수정하세요.

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**") // 모든 요청 경로에 대해
            .addResourceLocations("classpath:/static/") // static 폴더에서 리소스를 찾음
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    Resource requestedResource = location.createRelative(resourcePath);
                    // 요청된 리소스가 존재하면 그대로 반환, 없으면 index.html을 반환하여
                    // React Router가 클라이언트 사이드에서 라우팅을 처리하도록 함
                    if (requestedResource.exists() && requestedResource.isReadable()) {
                        return requestedResource;
                    } else {
                        return new ClassPathResource("/static/index.html");
                    }
                }
            });
    }
}
