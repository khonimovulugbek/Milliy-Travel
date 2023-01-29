package milliy.anonymous.milliytravel.config;

import lombok.RequiredArgsConstructor;
import milliy.anonymous.milliytravel.config.filter.JwtFilter;
import milliy.anonymous.milliytravel.service.details.CustomProfileDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    private final CustomProfileDetailsService customProfileDetailsService;

    private final JwtFilter jwtFilter;

    private final ObjectPostProcessor<Object> objectPostProcessor;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean("authenticationManager")
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .userDetailsService(customProfileDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and().getOrBuild();
    }


    @Bean("filterChain")
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated();

        http.cors().and().csrf().disable();
        return http.build();
    }
}
