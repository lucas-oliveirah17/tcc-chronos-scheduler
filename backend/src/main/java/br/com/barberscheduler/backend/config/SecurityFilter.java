package br.com.barberscheduler.backend.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.barberscheduler.backend.repository.AutenticacaoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final AutenticacaoRepository autenticacaoRepository;
    
    public SecurityFilter(
            TokenService tokenService,
            AutenticacaoRepository autenticacaoRepository) {
        this.tokenService = tokenService;
        this.autenticacaoRepository = autenticacaoRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        var token = this.recuperarToken(request);
        
        if(token != null) {
            var subject = tokenService.validarToken(token);
            
            if(subject != null && !subject.isEmpty()) {
                UserDetails usuario = autenticacaoRepository.findByEmail(subject);
                
                if(usuario != null) {
                    var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }         
        }
        filterChain.doFilter(request, response);
    }
    
    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
