package sos_cidadao.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.experimental.var;
import sos_cidadao.api.model.Abrigado;
import sos_cidadao.api.model.Abrigo;
import sos_cidadao.api.model.Endereco;
import sos_cidadao.api.model.Usuario;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.model.enums.TipoUsuario;
import sos_cidadao.api.model.enums.UserRole;
import sos_cidadao.api.repository.AbrigadoRepository;
import sos_cidadao.api.repository.AbrigoRepository;
import sos_cidadao.api.repository.UsuarioRepository;
import sos_cidadao.api.repository.VoluntarioRepository;

@Component
public class DatabaseSeeder {
    
    @Autowired UsuarioRepository usuarioRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired VoluntarioRepository voluntarioRepository;
    @Autowired AbrigoRepository abrigoRepository;
    @Autowired AbrigadoRepository abrigadoRepository;

    @PostConstruct
    public void init(){
        var usuarios = List.of(
            Usuario.builder()
                .nome("Samuel")
                .email("samuel@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.VOLUNTARIO)
                .role(UserRole.ADMIN)
                .build(),

            Usuario.builder()
                .nome("Lopes")
                .email("lopes@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.VOLUNTARIO)
                .role(UserRole.ADMIN)
                .build()


        );
        usuarioRepository.saveAll(usuarios);

        var voluntarios = List.of(
            Voluntario.builder()
                .usuario(usuarios.get(0))
                .habilidades("Cuidado de Idosos")
                .disponivel(true)
                .build(),

            Voluntario.builder()
                .usuario(usuarios.get(1))
                .habilidades("Cuidado de Crianças")
                .disponivel(true)
                .build()
            );

        voluntarioRepository.saveAll(voluntarios);

        var abrigos = List.of(
            Abrigo.builder()
                .endereco(Endereco.builder()
                    .bairro("Jardim")
                    .cep("18238-10")
                    .cidade("São Paulo")    
                    .estado("SP")
                    .numero(12)
                    .rua("Rua boa")
                    .pais("Brasil")
                    .build())
                .lotacao(100)
                .nome("Lar do Amor")
                .build()
            );

        abrigoRepository.saveAll(abrigos);

        var abrigado = 
        Abrigado.builder()
            .usuario(Usuario.builder()
                .nome("Matheus")
                .email("matheus@gmail.com")
                .senha(passwordEncoder.encode("123"))
                .tipo(TipoUsuario.ABRIGADO)
                .role(UserRole.USER)
                .build())
            .abrigo(Abrigo.builder()
                .endereco(Endereco.builder()
                    .bairro("Jardim bebe")
                    .cep("31232-100")
                    .cidade("São Paulo")    
                    .estado("SP")
                    .numero(69)
                    .rua("Rua dos Anjos")
                    .pais("Brasil")
                    .build())
                .lotacao(100)
                .nome("Lar da Esperança")
                .build())
            .necessidadesEspecificas("Fome")
            .idade(10)
            .sexo("Masculino")
            .build();
        abrigadoRepository.save(abrigado);


    }
}
