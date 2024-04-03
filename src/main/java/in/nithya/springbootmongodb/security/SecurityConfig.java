package in.nithya.springbootmongodb.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtConverter jwtConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) ->
                authz.requestMatchers(HttpMethod.POST, "/todosPost").permitAll()
                        .requestMatchers(HttpMethod.GET, "/todos").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/todos/{id}").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/todosu/{id}").hasAnyRole(ADMIN,USER)
                        .requestMatchers(HttpMethod.DELETE, "/todos/{id}").hasRole(ADMIN)
                        .anyRequest().authenticated());

        http.sessionManagement(sess -> sess.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
