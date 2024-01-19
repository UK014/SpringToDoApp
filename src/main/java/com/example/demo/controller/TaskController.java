package com.example.demo.controller;

import com.example.demo.list.Task;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/create")
    public Task createTask(@RequestBody String task) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Task newtask = objectMapper.readValue(task,Task.class);
        List<Task> currentTasks = taskService.displayUnfinished();
        for (int i = 0; i < currentTasks.size();i++){
            if (newtask.getTask().equals(currentTasks.get(i).getTask())){
                System.out.println("You already have a task called " +currentTasks.get(i).getTask());
                return currentTasks.get(i);
            }
        }
        return taskService.createTask(newtask);
    }

    @GetMapping("/tasks")
    public List<Task> displayAllTasks(){
        return taskService.displayAllTasks();
    }

    @PostMapping("/update")
    public Task updateTask(@RequestBody String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Task updateTask = objectMapper.readValue(request,Task.class);
        return taskService.updateTask(updateTask);
    }

    @PostMapping("/delete")
    public Task deleteTask(@RequestBody String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Task deleteTask = objectMapper.readValue(request,Task.class);
        return taskService.deleteTask(deleteTask);
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteWithId(@PathVariable Long id){
        return taskService.deleteWithId(id);
    }

    @GetMapping("/tasks/{id}")
    public Optional<Task> displayTask(@PathVariable Long id){

        return taskService.displaySpesificTask(id);
    }

    @PostMapping("/tasks/{id}/{status}")
    public Optional<Task> updateStatus(@PathVariable Long id,@PathVariable String status){
        return taskService.updateStatus(id,status);
    }

    @GetMapping("/tasks/status/{status}")
    public List<Task> displayByStatus(@PathVariable String status){
        return taskService.displayByStatus(status);
    }

    @GetMapping("tasks/unfinished")
    public List<Task> displayUnfinished(){
        return taskService.displayUnfinished();
    }
}
