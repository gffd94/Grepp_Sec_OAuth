package io.gffd94.security_oauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin.disable())
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/login", "/sing-up")
                            .anonymous()
                            .requestMatchers("/user/**")
                            //hasAnyAuthority : requestMathcer인 접근자가 해당 권한만 갖고 있는 사람만 접근 가능하다고 권한에 대한 범위설정.
                            .hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers("/admin/**")
                            .hasAnyAuthority("ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .build();
    }

}
