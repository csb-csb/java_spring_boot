package com.sfac.javaSpringBoot.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


    @Configuration
    @AutoConfigureAfter({WebMvcAutoConfiguration.class})
    public class WebMvcConfig {

        @Value("${server.http.port}")
        private int httpPort;

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
    }




