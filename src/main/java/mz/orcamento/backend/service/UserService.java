package mz.orcamento.backend.service;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.user.LoginRequestDTO;
import mz.orcamento.backend.dto.user.LoginResponseDTO;
import mz.orcamento.backend.dto.user.UserRequestDTO;
import mz.orcamento.backend.config.TokenService;
import mz.orcamento.backend.dto.user.UserResponseDTO;
import mz.orcamento.backend.model.Municipio;
import mz.orcamento.backend.model.User;
import mz.orcamento.backend.repository.MunicipioRepository;
import mz.orcamento.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

    @Service
    @RequiredArgsConstructor // Substitui o @Autowired nos campos, prática recomendada (Injeção via Construtor)
    public class UserService {

        private final UserRepository userRepository;
        private final MunicipioRepository municipioRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final TokenService tokenService;

        /**
         * Registo de Utilizador
         * Metodologia: Validação de unicidade e Criptografia Hashing (BCrypt)
         * antes da persistência para garantir o princípio de 'Security by Design'.
         */
        @Transactional
        public UserResponseDTO registarUser(UserRequestDTO data) {

            // 1. Verificação de Integridade (Regra de Negócio)
            if (userRepository.findByEmail(data.email()).isPresent()) {
                throw new RuntimeException("O email informado já está registado no sistema.");
            }

            // 2. Localização da Entidade Relacionada (Município)
            Municipio municipio = municipioRepository.findById(data.municipioId())
                    .orElseThrow(() -> new RuntimeException("Município não localizado."));

            // 3. Aplicação de Criptografia Unidirecional (Segurança)
            String senhaCriptografada = passwordEncoder.encode(data.senha());

            // 4. Mapeamento de DTO para Entidade
            User newUser = new User();
            newUser.setNomeUsuario(data.nomeUsuario());
            newUser.setEmail(data.email());
            newUser.setSenha(senhaCriptografada);
            newUser.setNumeroBI(data.numeroBI());
            newUser.setTelefone(data.telefone());
            newUser.setMunicipio(municipio);
            newUser.setPerfilAcesso(data.perfilAcesso());

            // 5. Persistência e Retorno de Projeção (ResponseDTO)
            userRepository.save(newUser);

            return new UserResponseDTO(
                    newUser.getId(),
                    newUser.getNomeUsuario(),
                    newUser.getEmail(),
                    newUser.getNumeroBI(),
                    newUser.getTelefone(),
                    municipio.getNomeAutarquia(),
                    newUser.getPerfilAcesso().name()
            );
        }
        //Autenticacao

        public LoginResponseDTO autenticar(LoginRequestDTO data){

            // 1. Cria o objeto de autenticação com as credenciais fornecidas
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

            // 2. O AuthenticationManager consulta o UserDetailsService e valida a senha (BCrypt)
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // 3. Gera o token JWT para o utilizador autenticado
            var token = tokenService.generateToken((User) auth.getPrincipal());

            // 4. Retorna o DTO com o token e os dados do perfil para o Frontend (React)
            User user = (User) auth.getPrincipal();
            return new LoginResponseDTO(
                    token,
                    "Bearer",
                    new UserResponseDTO(
                            user.getId(),
                            user.getNomeUsuario(),
                            user.getEmail(),
                            user.getNumeroBI(),
                            user.getTelefone(),
                            user.getMunicipio().getNomeAutarquia(),
                            user.getPerfilAcesso().name()
                    )
            );
        }

        /**
         •	buscarPerfil: Retornar os dados do usuário logado para o frontend.
         */
        public UserResponseDTO buscarPerfil() {
            // 1. Recupera o objeto de autenticação do contexto do Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("Utilizador não autenticado.");
            }

            // 2. O Principal contém a nossa entidade User (que implementa UserDetails)
            User user = (User) authentication.getPrincipal();

            // 3. Retorna a projeção segura (DTO)
            return new UserResponseDTO(
                    user.getId(),
                    user.getNomeUsuario(),
                    user.getEmail(),
                    user.getNumeroBI(),
                    user.getTelefone(),
                    user.getMunicipio().getNomeAutarquia(),
                    user.getPerfilAcesso().name()
            );
        }
    }