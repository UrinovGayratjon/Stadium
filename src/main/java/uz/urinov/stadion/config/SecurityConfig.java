package uz.urinov.stadion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.urinov.stadion.entity.enums.RoleEnum;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrfConfigurer) -> csrfConfigurer.disable())
                .authorizeRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers("/api/v1/orders/**").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.USER.name(), RoleEnum.STADIUM_OWNER.name())
                .requestMatchers("/api/v1/stadiums/*").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.USER.name(), RoleEnum.STADIUM_OWNER.name())
                .requestMatchers("/api/v1/stadiums/**").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.STADIUM_OWNER.name())
                .anyRequest().authenticated();
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
