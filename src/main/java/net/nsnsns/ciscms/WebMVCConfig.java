package net.nsnsns.ciscms;

import net.nsnsns.ciscms.protocols.TimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

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

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setCacheable(false);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addConverter(new TimeConverter());

    }
}
