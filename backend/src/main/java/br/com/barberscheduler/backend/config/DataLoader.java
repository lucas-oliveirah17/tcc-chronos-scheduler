package br.com.barberscheduler.backend.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.com.barberscheduler.backend.model.Agendamento;
import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
import br.com.barberscheduler.backend.model.enums.StatusAgendamento;

import br.com.barberscheduler.backend.repository.AgendamentoRepository;
import br.com.barberscheduler.backend.repository.ProfissionalRepository;
import br.com.barberscheduler.backend.repository.ServicoRepository;
import br.com.barberscheduler.backend.repository.UsuarioRepository;


@Configuration
@Profile("seed")
public class DataLoader implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataLoader(
            UsuarioRepository usuarioRepository,
            ServicoRepository servicoRepository,
            ProfissionalRepository profissionalRepository,
            AgendamentoRepository agendamentoRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.servicoRepository = servicoRepository;
        this.profissionalRepository=  profissionalRepository;
        this.agendamentoRepository=  agendamentoRepository;
        this.passwordEncoder = passwordEncoder;    
    }
    
    @Override
    @Transactional // Garante que tudo rode em uma única transação
    public void run(String... args) throws Exception {
        
        // 2. Trava de segurança: só popula se o banco estiver vazio
        if (usuarioRepository.count() > 0) {
            System.out.println(">>> O banco de dados já está populado. Ignorando o script de seed.");
            return;
        }

        System.out.println(">>> Iniciando script de 'seed' do banco de dados...");

        // 3. Criar e salvar os dados
        List<Servico> servicos = criarServicos();
        criarAdmins();
        List<Usuario> clientes = criarClientes();
        List<Profissional> profissionais = criarProfissionais();
        criarAgendamentos(clientes, profissionais, servicos);

        System.out.println(">>> Seed do banco de dados concluído com sucesso.");
    }

    // --- Métodos Auxiliares para Criar Entidades ---

    private List<Servico> criarServicos() {
        List<Servico> servicos = List.of(
            new Servico("Corte Masculino", "Corte com máquina e tesoura", 30, new BigDecimal("50.00")),
            new Servico("Barba Completa", "Barba com toalha quente e navalha", 45, new BigDecimal("45.00")),
            new Servico("Corte e Barba", "Combo completo", 75, new BigDecimal("90.00")),
            new Servico("Pezinho", "Acabamento e contorno", 15, new BigDecimal("20.00")),
            new Servico("Hidratação Capilar", "Tratamento para os fios", 30, new BigDecimal("60.00"))
        );
        return servicoRepository.saveAll(servicos);
    }

    private List<Usuario> criarAdmins() {
        List<Usuario> admins = List.of(
            new Usuario("Admin Daniel", "daniel@admin", passwordEncoder.encode("admin123"), "11999990001", PerfilUsuario.ADMINISTRADOR),
            new Usuario("Admin Lucas", "lucas@admin", passwordEncoder.encode("admin123"), "11999990002", PerfilUsuario.ADMINISTRADOR)
        );
        return usuarioRepository.saveAll(admins);
    }

    private List<Usuario> criarClientes() {
        List<Usuario> clientes = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            clientes.add(new Usuario(
                "Cliente " + i,
                "cliente" + i + "@cliente",
                passwordEncoder.encode("cliente123"),
                "1198888" + String.format("%04d", i),
                PerfilUsuario.CLIENTE
            ));
        }
        return usuarioRepository.saveAll(clientes);
    }

    private List<Profissional> criarProfissionais() {
        List<Usuario> usuariosProfissionais = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            usuariosProfissionais.add(new Usuario(
                "Barbeiro " + i,
                "profissional" + i + "@profissional",
                passwordEncoder.encode("profissional123"),
                "1197777" + String.format("%04d", i),
                PerfilUsuario.PROFISSIONAL
            ));
        }
        // 1. Salva os 'Usuarios' primeiro
        List<Usuario> usuariosSalvos = usuarioRepository.saveAll(usuariosProfissionais);

        List<Profissional> profissionais = new ArrayList<>();
        profissionais.add(new Profissional(usuariosSalvos.get(0), "Especialista em Corte Clássico"));
        profissionais.add(new Profissional(usuariosSalvos.get(1), "Especialista em Barba"));
        profissionais.add(new Profissional(usuariosSalvos.get(2), "Coloração e Design"));
        profissionais.add(new Profissional(usuariosSalvos.get(3), "Cortes Modernos"));
        profissionais.add(new Profissional(usuariosSalvos.get(4), "Tratamento Capilar"));
        
        // 2. Salva os 'Profissionais' linkados
        return profissionalRepository.saveAll(profissionais);
    }

    private void criarAgendamentos(List<Usuario> clientes, List<Profissional> profissionais, List<Servico> servicos) {
        List<Agendamento> agendamentos = new ArrayList<>();
        Random rand = new Random();
        LocalDateTime agora = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);

        for (int i = 0; i < 20; i++) {
            Usuario cliente = clientes.get(rand.nextInt(clientes.size()));
            Profissional profissional = profissionais.get(rand.nextInt(profissionais.size()));
            Servico servico = servicos.get(rand.nextInt(servicos.size()));

            LocalDateTime inicio = agora.plusDays(rand.nextInt(7)).plusHours(rand.nextInt(8));
            LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

            // Simples checagem de conflito (apenas para o 'seed')
            LocalDateTime finalInicio = inicio;
            LocalDateTime finalFim = fim;
            Long profId = profissional.getId();
            boolean conflito = agendamentos.stream().anyMatch(a ->
                a.getProfissional().getId().equals(profId) &&
                a.getDataHoraInicio().isBefore(finalFim) &&
                a.getDataHoraFim().isAfter(finalInicio)
            );

            if (!conflito) {
                agendamentos.add(new Agendamento(
                    cliente,
                    profissional,
                    servico,
                    inicio,
                    fim,
                    StatusAgendamento.CONFIRMADO
                ));
            }
        }
        agendamentoRepository.saveAll(agendamentos);
    }
}
