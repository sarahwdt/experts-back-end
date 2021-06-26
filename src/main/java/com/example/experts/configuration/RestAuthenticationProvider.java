package com.example.experts.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Класс указывающий серверу, что необходимо сообщать пользователю если введен неверный пароль
 */
public class RestAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        if (!this.getPasswordEncoder().matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Неправильный пароль");
    }

}
