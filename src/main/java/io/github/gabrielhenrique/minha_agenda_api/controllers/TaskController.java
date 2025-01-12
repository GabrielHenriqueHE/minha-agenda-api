package io.github.gabrielhenrique.minha_agenda_api.controllers;

import io.github.gabrielhenrique.minha_agenda_api.domain.task.Task;
import io.github.gabrielhenrique.minha_agenda_api.domain.task.TaskDTO;
import io.github.gabrielhenrique.minha_agenda_api.domain.task.TaskRegisterDTO;
import io.github.gabrielhenrique.minha_agenda_api.payloads.ApiResponse;
import io.github.gabrielhenrique.minha_agenda_api.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @GetMapping()
    private ResponseEntity<ApiResponse<List<TaskDTO>>> getTasks() {

        List<TaskDTO> tasks = this.taskService.getTasks().stream().map(
                task -> new TaskDTO(task.getId(), task.getTitle(), task.getDate(), task.getDescription(), task.getStatus())
        ).toList();

        ApiResponse<List<TaskDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), tasks, null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @PostMapping("/new")
    private ResponseEntity<ApiResponse<TaskDTO>> createTask(@RequestBody TaskRegisterDTO data) {

        Task task = this.taskService.createTask(data);
        TaskDTO dto = new TaskDTO(task.getId(), task.getTitle(), task.getDate(), task.getDescription(), task.getStatus());

        ApiResponse<TaskDTO> response = new ApiResponse<>(HttpStatus.OK.value(), dto, null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("{id}/update")
    private ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable Long id, @RequestBody TaskRegisterDTO data) {

        Task task = this.taskService.updateTask(id, data);
        TaskDTO dto = new TaskDTO(task.getId(), task.getTitle(), task.getDate(), task.getDescription(), task.getStatus());

        ApiResponse<TaskDTO> response = new ApiResponse<>(HttpStatus.OK.value(), dto, null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}/delete")
    private ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);

        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "Task deleted successfully.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
