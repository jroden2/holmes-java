package com.buildtechwithJack.holmes.configs;

import org.springframework.boot.web.error.ErrorPage;
import org.springframework.boot.web.error.ErrorPageRegistrar;
import org.springframework.boot.web.error.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class WebConfiguration implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
                new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405")
        );
    }
}

