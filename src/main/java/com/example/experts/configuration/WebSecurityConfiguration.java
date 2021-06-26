package com.example.experts.configuration;

import com.example.experts.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Класс-конфигуратор модуля защиты
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    protected final UserService userService;

    /**
     * Бин конфигурирующий каким сервисом будет осуществлятся работа с пользователями
     * и какое будет использовано шифрование пароля
     */
    @Bean
    public RestAuthenticationProvider restAuthenticationProvider() {
        RestAuthenticationProvider authProvider = new RestAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    /**
     * Конфигурирование модуля защиты
     * Установка необходимых привелегий для доступа к ресурсам
     * Отключение лишних технологий
     * Описание процесса авторизации
     * Указание обработчиков для ситуаций удачной/неудачной авторизации, выхода из системы
     * Указание обработчика ситуации запрета доступа
     * @param http контекст
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().disable().cors();
        http.authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/auth/reg").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().formLogin()
                .loginPage("/api/auth").permitAll()
                .loginProcessingUrl("/api/auth").permitAll()
                .usernameParameter("login")
                .successHandler(new RestAuthenticationResultHandler())
                .failureHandler(new RestAuthenticationResultHandler())
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                .logoutSuccessHandler(new RestLogoutResultHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and().exceptionHandling().authenticationEntryPoint(new RestEntryPoint());
    }

    /**
     * Бин указывающий используемое шифрование пароля
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Установка конфигурации
     */
    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.authenticationProvider(restAuthenticationProvider());
    }

}
