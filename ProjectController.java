package com.devshowcase.controller;

import com.devshowcase.model.Project;
import com.devshowcase.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<Map<String, Object>> addFeedback(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("reviewerName", payload.get("reviewerName"));
        response.put("comment", payload.get("comment"));
        response.put("rating", payload.get("rating"));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<Map<String, String>> upvoteProject(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Curtida adicionada com sucesso!");
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Recurso nao encontrado (404)");
        error.put("message", "O ID solicitado nao existe.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
