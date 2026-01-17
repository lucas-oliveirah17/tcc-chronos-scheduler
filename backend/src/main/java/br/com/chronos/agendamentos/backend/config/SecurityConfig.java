package br.com.chronos.agendamentos.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final SecurityFilter securityFilter;
    
    public SecurityConfig(
            SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
    
    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy = """
	        ROLE_ADMINISTRADOR > ROLE_PROFISSIONAL
	        ROLE_PROFISSIONAL > ROLE_CLIENTE
	        """;
        
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            // Desabilita o CSRF (nessessário para APIs stateless/Postman)
            // Necessário para operações POST e PUT do Postman e Frontend
            .csrf(csrf -> csrf.disable())
            
            .cors(_ -> {})      
            // //Define a política de sessão como STATELESS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Define quais requisições https são autorizadas/autenticadas
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
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
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // AS SUAS URLs DE ORIGEM PERMITIDAS
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",
            "https://chronos-agendamentos.vercel.app"
        ));
        
        // MÉTODOS PERMITIDOS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // HEADERS PERMITIDOS
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // CREDENCIAIS (Se o seu front-end enviar cookies ou auth headers)
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica essa configuração a todas as rotas que começam com /api/
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}