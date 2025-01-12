package io.github.gabrielhenrique.minha_agenda_api.services;

import io.github.gabrielhenrique.minha_agenda_api.domain.task.Task;
import io.github.gabrielhenrique.minha_agenda_api.domain.task.TaskRegisterDTO;
import io.github.gabrielhenrique.minha_agenda_api.domain.task.TaskStatus;
import io.github.gabrielhenrique.minha_agenda_api.exceptions.InvalidDateFormatException;
import io.github.gabrielhenrique.minha_agenda_api.exceptions.InvalidStatusException;
import io.github.gabrielhenrique.minha_agenda_api.exceptions.InvalidTextInputException;
import io.github.gabrielhenrique.minha_agenda_api.exceptions.ResourceNotFoundException;
import io.github.gabrielhenrique.minha_agenda_api.repositories.TaskRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task createTask(TaskRegisterDTO data) {

        this.validateData(data);

        LocalDateTime parsedDate = parseDate(data.date());
        TaskStatus status = TaskStatus.valueOf(data.status());

        Task task = new Task(
            data.title(),
            parsedDate,
            data.description(),
            status
        );

        return this.taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, TaskRegisterDTO data) {

        var optionalTask = this.taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }

        Task task = optionalTask.get();

        if (data.title() != null && !data.title().isEmpty()) {
            if (data.title().length() < 3) {
                throw new InvalidTextInputException("Title is too short.");
            }

            task.setTitle(data.title());
        }

        if (data.date() != null && !data.date().isEmpty()) {
            try {
                LocalDateTime date = parseDate(data.date());

                task.setDate(date);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException(data.date() + "is not a valid date. Expected: dd/MM/yy HH:mm");
            }
        }

        if (data.description() != null && !data.description().isEmpty()) {
            if (data.description().length() < 3) {
                throw new InvalidTextInputException("Description is too short.");
            }

            task.setDescription(data.description());
        }

        if (data.status() != null) {
            if (!isValidStatus(data.status())) {
                throw new InvalidStatusException(data.status() + " is not a valid status.");
            }

            TaskStatus status = TaskStatus.valueOf(data.status());

            task.setStatus(status);
        }

        return this.taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        var optionalTask = this.taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }

        Task task = optionalTask.get();

        this.taskRepository.delete(task);
    }

    @Transactional
    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    private boolean isValidStatus(String value) {
        List<Boolean> matches = Arrays.stream(TaskStatus.values()).map(mapValue -> mapValue.getStatus().matches(value)).toList();

        return matches.contains(true);
    }

    private void validateData(TaskRegisterDTO data) {
        if (data.title().isEmpty() || data.title().length() <= 3) {
            throw new InvalidTextInputException("Title is too short.");
        }

        try {
            parseDate(data.date());

        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(data.date() + "is not a valid date. Expected: dd/MM/yy HH:mm");
        }

        if (data.description().isEmpty() || data.description().length() < 3) {
            throw new InvalidTextInputException("Description is too short.");
        }

        if (!isValidStatus(data.status())) {
            throw new InvalidStatusException(data.status() + " is not a valid status.");
        }
    }

}
