package sos_cidadao.api.dto;

public record AbrigoDTO(
    String nome,
    String rua,
    int numero,
    String bairro,
    String cidade,
    String estado,
    String cep,
    String pais,
    int lotacao
) {}
