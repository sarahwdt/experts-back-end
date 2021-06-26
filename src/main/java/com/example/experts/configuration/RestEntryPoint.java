package com.example.experts.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс переопреопределяющий ответ на попытку доступа неавторизованного пользователя
 */
public class RestEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Необходима авторизация");
    }
}
