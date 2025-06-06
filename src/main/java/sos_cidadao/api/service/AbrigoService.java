package sos_cidadao.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sos_cidadao.api.dto.AbrigoDTO;
import sos_cidadao.api.model.Abrigo;
import sos_cidadao.api.model.Endereco;
import sos_cidadao.api.repository.AbrigoRepository;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Transactional
    public Abrigo criarAbrigo(AbrigoDTO dto) {
        if (dto.lotacao() <= 0) {
            throw new IllegalArgumentException("A lotação do abrigo deve ser maior que zero");
        }
        Endereco endereco = Endereco.builder()
                .rua(dto.rua())
                .numero(dto.numero())
                .bairro(dto.bairro())
                .cidade(dto.cidade())
                .estado(dto.estado())
                .cep(dto.cep())
                .pais(dto.pais())
                .build();
        Abrigo novoAbrigo = Abrigo.builder()
                .nome(dto.nome())
                .endereco(endereco)
                .lotacao(dto.lotacao())
                .build();
        return abrigoRepository.save(novoAbrigo);
    }

    public List<Abrigo> listarTodos() {
        return abrigoRepository.findAll();
    }

    public Optional<Abrigo> buscarPorId(Long id) {
        return abrigoRepository.findById(id);
    }

    @Transactional
    public Abrigo atualizarAbrigo(Long id, AbrigoDTO dto) {
        Abrigo abrigo = abrigoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Abrigo não encontrado"));
        abrigo.setNome(dto.nome());
        Endereco endereco = abrigo.getEndereco();
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setPais(dto.pais());
        abrigo.setLotacao(dto.lotacao());
        return abrigoRepository.save(abrigo);
    }

    @Transactional
    public void deletarAbrigo(Long id) {
        abrigoRepository.deleteById(id);
    }
}
