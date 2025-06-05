package sos_cidadao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.repository.VoluntarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Transactional
    public Voluntario criarVoluntario(Voluntario voluntario) {
        return voluntarioRepository.save(voluntario);
    }

    public List<Voluntario> listarTodos() {
        return voluntarioRepository.findAll();
    }

    public Optional<Voluntario> buscarPorId(String id) {
        return voluntarioRepository.findById(id);
    }

    @Transactional
    public Voluntario atualizarVoluntario(String id, Voluntario voluntarioAtualizado) {
        Voluntario voluntario = voluntarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Voluntario não encontrado"));
        voluntario.setHabilidades(voluntarioAtualizado.getHabilidades());
        voluntario.setDisponivel(voluntarioAtualizado.isDisponivel()); // Atualiza para false

        return voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void deletarVoluntario(String id) {
        voluntarioRepository.deleteById(id);
    }
}
