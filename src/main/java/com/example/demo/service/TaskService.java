package com.example.demo.service;

import com.example.demo.list.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        task.setTime(LocalDateTime.now());
        return taskRepository.save(task);
    }
    public List<Task> displayAllTasks(){
        return taskRepository.findAll();
    }

    @Transactional
    public Task updateTask(Task task){
        taskRepository.updateTask(task.getId(),
                task.getTask(),
                task.getPriority(),
                LocalDateTime.now(),
                task.getStatus());

        return task;
    }

    public Task deleteTask(Task task){
        taskRepository.deleteById(task.getId());
        return task;
    }

    public Optional<Task> displaySpesificTask(Long id){
        Optional<Task> task = taskRepository.findById(id);
        return task;
    }

    @Transactional
    public Optional<Task> updateStatus(Long id, String status){
        taskRepository.updateStatus(id, status);
        Optional<Task> task = taskRepository.findById(id);
        return task;
    }

    public List<Task> displayByStatus(String status){
        List<Task> tasks = new ArrayList<>();
        tasks = taskRepository.findAllByStatus(status);
        return tasks;
    }

    public List<Task> displayUnfinished(){
        List<Task> tasks = new ArrayList<>();
        tasks = taskRepository.findUnfinished();
        return tasks;
    }

    public String deleteWithId(Long id){
        taskRepository.deleteById(id);
        return "Task deleted succesfully";
    }
}
