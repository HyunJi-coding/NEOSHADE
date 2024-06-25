package org.example.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);

        registry.addViewController("/home").setViewName("/home/index");
        registry.addViewController("/signin").setViewName("/login/login");
        registry.addViewController("/signup").setViewName("/login/signup");
        registry.addViewController("/productlist").setViewName("/product/productlist");



    }
}
