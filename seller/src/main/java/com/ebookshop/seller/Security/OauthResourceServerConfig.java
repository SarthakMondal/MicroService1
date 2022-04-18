package com.ebookshop.seller.Security;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class OauthResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf() .disable()
                .authorizeRequests()
                .antMatchers("/backend/seller/protected/**").authenticated()
                .antMatchers("/backend/seller/public/**").permitAll()
                .and()
                .formLogin().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().cors();

    }

}
