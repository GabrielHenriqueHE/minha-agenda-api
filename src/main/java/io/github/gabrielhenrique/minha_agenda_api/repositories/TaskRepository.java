package io.github.gabrielhenrique.minha_agenda_api.repositories;

import io.github.gabrielhenrique.minha_agenda_api.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {



}
