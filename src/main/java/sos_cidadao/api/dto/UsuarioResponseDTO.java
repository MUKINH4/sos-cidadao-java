package sos_cidadao.api.dto;

import sos_cidadao.api.model.enums.UserRole;

public record UsuarioResponseDTO(
    String id, String email, UserRole role
){}
