package A2MinhNB_HE191060.SE1941JV.config;

import A2MinhNB_HE191060.SE1941JV.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Public: view active news only (controller enforces status=1)
                        .requestMatchers(HttpMethod.GET, "/api/news", "/api/news/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/news/*/edit").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/accounts/me", "/api/accounts/me/**", "/api/accounts/profile",
                                "/api/accounts/profile/**")
                        .authenticated()
                        // Admin: account management
                        .requestMatchers("/api/accounts/**").hasRole("ADMIN")
                        // Staff: categories, news CRUD, tags, my news
                        // Note: Admin might need GET access for Dashboard stats
                        .requestMatchers(HttpMethod.GET, "/api/categories/**", "/api/tags/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories/**", "/api/tags/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**", "/api/tags/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**", "/api/tags/**").hasRole("STAFF")
                        
                        .requestMatchers("/api/my/news/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/news").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/news/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/news/**").hasRole("STAFF")
                        
                        .requestMatchers("/api/auth/me", "/api/accounts/me/**", "/api/accounts/profile/**")
                        .authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
