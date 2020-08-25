package com.sfac.javaSpringBoot.config;

import com.sfac.javaSpringBoot.filter.RequestParamaFilter;
import com.sfac.javaSpringBoot.interceptor.RequestViewInterceptor;
import com.sfac.javaSpringBoot.utils.RedisUtils;
import io.netty.util.internal.ResourcesUtil;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
    @AutoConfigureAfter({WebMvcAutoConfiguration.class})
    public class WebMvcConfig implements WebMvcConfigurer {

        @Value("${server.http.port}")
        private int httpPort;

     /*  @Value("${server.http.port}")
       private int httpPort;*/
        @Autowired
        private RequestViewInterceptor requestViewInterceptor;

        @Autowired
        private ResourceConfigBean resourceConfigBean;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName=System.getProperty("os.name");
        if (osName.startsWith("win")){
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern()).
                    addResourceLocations(ResourceUtils.FILE_URL_PREFIX +resourceConfigBean.
                            getLocationPathForWindows());
        }else {
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern()).
                    addResourceLocations(ResourceUtils.FILE_URL_PREFIX +
                            resourceConfigBean.getLocationPathForLinux());
        }
    }

    @Bean
        public Connector connector() {
            Connector connector = new Connector();
            connector.setPort(httpPort);
            connector.setScheme("http");
            return connector;
        }

        @Bean
        public ServletWebServerFactory webServerFactory() {
            TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
            tomcat.addAdditionalTomcatConnectors(connector());
            return tomcat;
        }


    @Bean//过滤器
    public FilterRegistrationBean<RequestParamaFilter> register() {
        FilterRegistrationBean<RequestParamaFilter> register =
                new FilterRegistrationBean<RequestParamaFilter>();

        register.setFilter(new RequestParamaFilter());
        return register;
    }

    @Override//拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestViewInterceptor).addPathPatterns("/**");
    }
    }




