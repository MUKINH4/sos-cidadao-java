package sos_cidadao.api.dto;

public record AbrigadoDTO(
    String nome,
    int idade,
    String sexo,
    int abrigoId,
    String necessidadesEspecificas
) {}
