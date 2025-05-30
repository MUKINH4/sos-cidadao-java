package sos_cidadao.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sos_cidadao.api.model.Abrigo;
import sos_cidadao.api.repository.AbrigoRepository;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Transactional
    public Abrigo criarAbrigo(Abrigo abrigo) {
        return abrigoRepository.save(abrigo);
    }

    public List<Abrigo> listarTodos() {
        return abrigoRepository.findAll();
    }

    public Optional<Abrigo> buscarPorId(Long id) {
        return abrigoRepository.findById(id);
    }

    @Transactional
    public Abrigo atualizarAbrigo(Long id, Abrigo abrigoAtualizado) {
        Abrigo abrigo = abrigoRepository.findById(id).orElseThrow(() -> new RuntimeException("Abrigo não encontrado"));
        abrigo.setNome(abrigoAtualizado.getNome());
        abrigo.setEndereco(abrigoAtualizado.getEndereco());
        return abrigoRepository.save(abrigo);
    }

    @Transactional
    public void deletarAbrigo(Long id) {
        abrigoRepository.deleteById(id);
    }
}
