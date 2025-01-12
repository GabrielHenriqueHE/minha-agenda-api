package io.github.gabrielhenrique.minha_agenda_api.domain.task;

public record TaskRegisterDTO(
    String title,
    String date,
    String description,
    String status
) {
}
