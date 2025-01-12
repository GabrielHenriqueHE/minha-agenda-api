package io.github.gabrielhenrique.minha_agenda_api.domain.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("PENDING"),
    CONCLUDED("CONCLUDED"),
    OVERDUE("OVERDUE");

    private String status;

    TaskStatus(String status) {
        this.status = status;
    }
}
