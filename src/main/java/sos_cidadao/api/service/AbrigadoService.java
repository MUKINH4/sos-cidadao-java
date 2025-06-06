package sos_cidadao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sos_cidadao.api.dto.AbrigadoDTO;
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
    public AbrigadoDTO criarAbrigado(AbrigadoDTO dto) {
        if (dto.abrigoId() <= 0) {
            throw new IllegalArgumentException("O abrigo deve ser informado com um ID válido");
        }
        Abrigo abrigo = abrigoRepository.findById((long) dto.abrigoId())
            .orElseThrow(() -> new RuntimeException("Abrigo não encontrado"));
        Abrigado entity = Abrigado.builder()
            .nome(dto.nome())
            .idade(dto.idade())
            .sexo(dto.sexo())
            .necessidadesEspecificas(dto.necessidadesEspecificas())
            .abrigo(abrigo)
            .build();
        Abrigado saved = abrigadoRepository.save(entity);
        return new AbrigadoDTO(
            saved.getNome(),
            saved.getIdade(),
            saved.getSexo(),
            saved.getAbrigo().getId().intValue(),
            saved.getNecessidadesEspecificas()
        );
    }

    public List<Abrigado> listarTodos() {
        return abrigadoRepository.findAll();
    }

    public Optional<Abrigado> buscarPorId(String id) {
        return abrigadoRepository.findById(id);
    }

    @Transactional
    public AbrigadoDTO atualizarAbrigado(String id, AbrigadoDTO dto) {
        Abrigado abrigado = abrigadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Abrigado não encontrado"));
        Abrigo abrigo = abrigoRepository.findById((long) dto.abrigoId())
            .orElseThrow(() -> new RuntimeException("Abrigo não encontrado"));
        abrigado.setNome(dto.nome());
        abrigado.setIdade(dto.idade());
        abrigado.setSexo(dto.sexo());
        abrigado.setNecessidadesEspecificas(dto.necessidadesEspecificas());
        abrigado.setAbrigo(abrigo);
        Abrigado updated = abrigadoRepository.save(abrigado);
        return new AbrigadoDTO(
            updated.getNome(),
            updated.getIdade(),
            updated.getSexo(),
            updated.getAbrigo().getId().intValue(),
            updated.getNecessidadesEspecificas()
        );
    }

    public List<AbrigadoDTO> listarTodosDtos() {
        return abrigadoRepository.findAll().stream()
            .map(a -> new AbrigadoDTO(a.getNome(), a.getIdade(), a.getSexo(), a.getAbrigo().getId().intValue(), a.getNecessidadesEspecificas()))
            .toList();
    }

    public Optional<AbrigadoDTO> buscarDtoPorId(String id) {
        return abrigadoRepository.findById(id)
            .map(a -> new AbrigadoDTO(a.getNome(), a.getIdade(), a.getSexo(), a.getAbrigo().getId().intValue(), a.getNecessidadesEspecificas()));
    }

    @Transactional
    public void deletarAbrigado(String id) {
        if (!abrigadoRepository.existsById(id)) {
            throw new RuntimeException("Abrigado não encontrado");
        }
        abrigadoRepository.deleteById(id);
    }
}
