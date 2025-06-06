package sos_cidadao.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sos_cidadao.api.dto.VoluntarioDTO;
import sos_cidadao.api.model.Voluntario;
import sos_cidadao.api.repository.VoluntarioRepository;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    public List<Voluntario> listarTodos() {
        return voluntarioRepository.findAll();
    }

    public Optional<Voluntario> buscarPorId(String id) {
        return voluntarioRepository.findById(id);
    }

    @Transactional
    public Voluntario atualizarVoluntario(String id, VoluntarioDTO voluntarioAtualizado) {
        Voluntario voluntario = voluntarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Voluntario não encontrado"));
        voluntario.setHabilidades(voluntarioAtualizado.habilidades());
        voluntario.setDisponivel(voluntarioAtualizado.disponivel()); // Atualiza para false

        return voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void deletarVoluntario(String id) {
        voluntarioRepository.deleteById(id);
    }
}
