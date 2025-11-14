package br.com.barberscheduler.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final SecurityFilter securityFilter;
    
    public SecurityConfig(
            SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            // Desabilita o CSRF (nessessário para APIs stateless/Postman)
            // Necessário para operações POST e PUT do Postman e Frontend
            .csrf(csrf -> csrf.disable())
            
         // //Define a política de sessão como STATELESS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Define quais requisições https são autorizadas/autenticadas
            .authorizeHttpRequests(authorize -> authorize
                    
                    // ENDPOINTS PÚBLICAS
                    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/servicos").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/profissionais").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/profissionais/**").permitAll()
                    
                    // ENDPOINTS DE CLIENTES
                    
                    // ENDPOINTS DE PROFISSIONAIS
                    
                    // ENDPOINTS DE ADMINISTRADOR
                    .anyRequest().hasRole("ADMINISTRADOR")             
            )
            
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build()
        ; 
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}