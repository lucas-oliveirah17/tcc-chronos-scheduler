package br.com.chronos.agendamentos.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chronos.agendamentos.backend.config.TokenService;
import br.com.chronos.agendamentos.backend.dto.AutenticacaoRequestDTO;
import br.com.chronos.agendamentos.backend.dto.AutenticacaoResponseDTO;
import br.com.chronos.agendamentos.backend.dto.UsuarioResponseDTO;
import br.com.chronos.agendamentos.backend.model.Usuario;
import br.com.chronos.agendamentos.backend.repository.AutenticacaoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/auth")
@Tag(name = "autenticacao")
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
    public ResponseEntity<AutenticacaoResponseDTO> login(
            @RequestBody AutenticacaoRequestDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();
        
        var token = tokenService.gerarToken(usuarioAutenticado);
        
        var usuarioResponseDTO = new UsuarioResponseDTO(usuarioAutenticado);
        
        return ResponseEntity.ok(new AutenticacaoResponseDTO(token, usuarioResponseDTO));
    }
}
