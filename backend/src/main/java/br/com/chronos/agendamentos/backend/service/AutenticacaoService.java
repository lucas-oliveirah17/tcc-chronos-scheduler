package br.com.chronos.agendamentos.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.chronos.agendamentos.backend.repository.AutenticacaoRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
    
    private final AutenticacaoRepository autenticacaoRepository;
    
    public AutenticacaoService(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return autenticacaoRepository.findByEmail(username);
    }
}
