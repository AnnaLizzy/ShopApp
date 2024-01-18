package com.example.shopapp.configurations;


import com.example.shopapp.filters.JwtTokenFilter;

import com.example.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.Arrays;
import java.util.List;


@EnableWebSecurity
@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request ->{
                    request
                            .requestMatchers(
                            String.format("%s/user/register",apiPrefix),
                            String.format("%s/user/login",apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/roles**",apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/categories/**",apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/categories**",apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/categories/**",apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/categories/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/products/**",apiPrefix)).hasAnyRole(Role.USER)

                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/products/**",apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/products/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products**",apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products/*",apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products/by-ids**",apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products/image/*",apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/orders/**",apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/orders/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/orders/**",apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/orders/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/orderDetails/**",apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/orderDetails/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/orderDetails/**",apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/orderDetails/**",apiPrefix)).hasRole(Role.ADMIN)
                            .anyRequest().authenticated();



                })
                .csrf(AbstractHttpConfigurer::disable);
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
