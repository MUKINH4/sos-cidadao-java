package sos_cidadao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sos_cidadao.api.model.Abrigado;
import sos_cidadao.api.model.Abrigo;
import sos_cidadao.api.repository.AbrigadoRepository;
import sos_cidadao.api.repository.AbrigoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigadoService {

    @Autowired
    private AbrigadoRepository abrigadoRepository;

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Transactional
    public Abrigado criarAbrigado(Abrigado abrigado) {
        if (abrigado.getAbrigo() == null || abrigado.getAbrigo().getId() == null) {
            throw new IllegalArgumentException("O abrigo deve ser informado com um ID válido");
        }

        Abrigo abrigo = abrigoRepository.findById(abrigado.getAbrigo().getId())
            .orElseThrow(() -> new RuntimeException("Abrigo não encontrado"));

        abrigado.setAbrigo(abrigo);
        return abrigadoRepository.save(abrigado);
    }

    public List<Abrigado> listarTodos() {
        return abrigadoRepository.findAll();
    }

    public Optional<Abrigado> buscarPorId(String id) {
        return abrigadoRepository.findById(id);
    }

    @Transactional
    public Abrigado atualizarAbrigado(String id, Abrigado abrigadoAtualizado) {
        Abrigado abrigado = abrigadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Abrigado não encontrado"));
        abrigado.setNecessidadesEspecificas(abrigadoAtualizado.getNecessidadesEspecificas());
        abrigado.setIdade(abrigadoAtualizado.getIdade());
        abrigado.setNome(abrigadoAtualizado.getNome());
        abrigado.setAbrigo(abrigadoAtualizado.getAbrigo());
        return abrigadoRepository.save(abrigado);
    }

    @Transactional
    public void deletarAbrigado(String id) {
        if (!abrigadoRepository.existsById(id)) {
            throw new RuntimeException("Abrigado não encontrado");
        }
        abrigadoRepository.deleteById(id);
    }
}
