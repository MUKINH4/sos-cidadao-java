package sos_cidadao.api.dto;

import sos_cidadao.api.model.enums.TipoUsuario;

public record UsuarioRequestDTO(
    String nome,
    String email, 
    String senha, 
    String confirmarSenha,
    TipoUsuario tipo
) {
}
