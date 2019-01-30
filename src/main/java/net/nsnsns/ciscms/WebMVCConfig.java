package net.nsnsns.ciscms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    /**
     * This override adds/configures simple view templates which don't require an external controller class
     *
     * @param registry Spring auto-injected ViewControllerRegistry
     */

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }


    /**
     * Configures paths for common static resources (images/css/js), these can then be referenced in Thymeleaf templates
     * via the resource handler path, e.g. /images/image.jpg
     *
     * @param registry Spring auto-injected ResourceHandlerRegistry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
    }
}
