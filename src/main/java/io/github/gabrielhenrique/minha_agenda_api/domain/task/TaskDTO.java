package io.github.gabrielhenrique.minha_agenda_api.domain.task;

import java.time.LocalDateTime;

public record TaskDTO(
    Long id,
    String title,
    LocalDateTime date,
    String description,
    TaskStatus status
) {
}
