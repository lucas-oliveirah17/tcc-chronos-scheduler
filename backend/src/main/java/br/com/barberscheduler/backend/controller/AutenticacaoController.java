package br.com.barberscheduler.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barberscheduler.backend.config.TokenService;
import br.com.barberscheduler.backend.dto.AutenticacaoDTO;
import br.com.barberscheduler.backend.dto.TokenDTO;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.repository.AutenticacaoRepository;


@RestController
@RequestMapping("api/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    
    AutenticacaoController(
            AuthenticationManager authenticationManager,
            AutenticacaoRepository autenticacaoRepository,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody AutenticacaoDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
